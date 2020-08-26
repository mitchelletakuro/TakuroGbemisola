package com.mitchelletakuro.gbemisolatakurofilter.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.mitchelletakuro.gbemisolatakurofilter.model.CarOwnerItem
import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

const val FILTER_INTENT_KEY: String = "filter_extra"
const val DEFAULT_SEPARATOR = ','
const val DEFAULT_QUOTE = '"'


/**
 * Utility function for assessing JSON file
 */
fun parseJson(context: Context, fileName: String): String? {
    val jsonString: String?
    try {
        val inputStream = context.assets.open(fileName)

        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        jsonString = String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return jsonString
}


/**
 * Checks if a volume containing external storage is available to at least read.
 */
fun isExternalStorageReadable(): Boolean {
    return Environment.getExternalStorageState() in setOf(
        Environment.MEDIA_MOUNTED,
        Environment.MEDIA_MOUNTED_READ_ONLY
    )
}

/**
 * Extension function that displays a toast
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Open CSV file
 */
//fun parseCSVFile(fileName: String): List<Array<out String>> {
//    var items: List<Array<out String>> = emptyList()
//    try {
//        runBlocking {
//            val reader = CSVReader(FileReader(fileName))
//            items = reader.readAll()
//        }
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    return items
//}

fun readDataFromRaw(): ArrayList<CarOwnerItem> {
    val carOwners = ArrayList<CarOwnerItem>()
    try {
        val file = File("/storage/emulated/0/FilterApp/car_owners_data.csv")
        val reader = FileReader(file)
        val bufferedReader = BufferedReader(reader)
        var line: String?
        bufferedReader.readLine() //Step over the header
        line = bufferedReader.readLine()
        while (line != null) {
            val carOwnerTokens = csvDelimiter(line,  DEFAULT_SEPARATOR, DEFAULT_QUOTE)
            if (carOwnerTokens.isNotEmpty()) {
                val carOwner = CarOwnerItem(
                    carOwnerTokens[0].toInt(),
                    carOwnerTokens[1],
                    carOwnerTokens[2],
                    carOwnerTokens[3],
                    carOwnerTokens[4],
                    carOwnerTokens[5],
                    carOwnerTokens[6].toInt(),
                    carOwnerTokens[7],
                    carOwnerTokens[8],
                    carOwnerTokens[9],
                    carOwnerTokens[10]
                )



                carOwners.add(carOwner)
            }
            line = bufferedReader.readLine()
        }
    } catch (e: IOException) {
        Log.e(TAG, e.message, e)
        throw e
    }
    return carOwners
}


fun csvDelimiter(
    cvsLine: String,
    separators: Char,
    customQuote: Char
): List<String> {
    val result: MutableList<String> = ArrayList()

    if (cvsLine.isEmpty()) {
        return result
    }

    var curVal = StringBuffer()
    var inQuotes = false
    var startCollectChar = false
    var doubleQuotesInColumn = false
    val chars = cvsLine.toCharArray()
    for (ch in chars) {
        if (inQuotes) {
            startCollectChar = true
            if (ch == customQuote) {
                inQuotes = false
                doubleQuotesInColumn = false
            } else { //Fixed : allow "" in custom quote enclosed
                if (ch == '\"') {
                    if (!doubleQuotesInColumn) {
                        curVal.append(ch)
                        doubleQuotesInColumn = true
                    }
                } else {
                    curVal.append(ch)
                }
            }
        } else {
            if (ch == customQuote) {
                inQuotes = true
                //Fixed : allow "" in empty quote enclosed
                if (chars[0] != '"' && customQuote == '\"') {
                    curVal.append('"')
                }
                //double quotes in column will hit this!
                if (startCollectChar) {
                    curVal.append('"')
                }
            } else if (ch == separators) {
                result.add(curVal.toString())
                curVal = StringBuffer()
                startCollectChar = false
            } else if (ch == '\r') { //ignore LF characters
                continue
            } else if (ch == '\n') { //the end, break!
                break
            } else {
                curVal.append(ch)
            }
        }
    }
    result.add(curVal.toString())
    return result
}
fun filterCarOwners(filter: FilterItem, carOwners: List<CarOwnerItem>): List<CarOwnerItem> {
    return carOwners.filter { owner ->
        val containCountry = if (filter.countries.isNotEmpty()) {
            owner.country in filter.countries
        } else {
            true
        }
        val containsColor = if (filter.colors.isNotEmpty()) {
            owner.car_color in filter.colors
        } else {
            true
        }
        val containStartYear = if (filter.start_year != 0) {
            owner.car_model_year >= filter.start_year
        } else {
            true
        }
        val containEndYear = if (filter.end_year != 0) {
            owner.car_model_year <= filter.end_year
        } else {
            true
        }
        val containsGender = if (filter.gender.isNotEmpty()) {
            owner.gender.equals(filter.gender, true)
        } else {
            true
        }
        containCountry && containsColor && containStartYear && containEndYear && containsGender
    }
}


