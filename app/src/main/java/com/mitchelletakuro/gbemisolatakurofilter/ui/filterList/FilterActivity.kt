package com.mitchelletakuro.gbemisolatakurofilter.ui.filterList

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mitchelletakuro.gbemisolatakurofilter.databinding.ActivityMainBinding
import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import com.mitchelletakuro.gbemisolatakurofilter.ui.carowners.CarOwnersActivity
import com.mitchelletakuro.gbemisolatakurofilter.utils.*

class FilterActivity : AppCompatActivity(), OnAdapterItemClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jsonFileString = parseJson(applicationContext, "filter.json")
        val gson = Gson()
        val type = object : TypeToken<List<FilterItem>>() {}.type
        val filters = gson.fromJson<List<FilterItem>>(jsonFileString, type)
        val adapter =
            FilterAdapter(
                filters,
                this@FilterActivity
            )
        binding.filterRecyclerView.adapter = adapter
//        binding.filterRecyclerView.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            )
//        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {


                }
                else -> {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        12
                    )
                }
            }
        }

    }

    override fun onItemClick(item: FilterItem) {
        if (!isExternalStorageReadable())
            showToast("External storage is not readable")


        else {
            // TODO: prevent user from navigating to next activity if permission is not granted
            startActivity(
                Intent(this, CarOwnersActivity::class.java).putExtra(
                    FILTER_INTENT_KEY,
                    item
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            12 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    TODO("The user has granted storage permission")


                } else {
                    TODO("The user has rejected storage permission")



                }
                return
            }
            else -> {

            }
        }
    }
}
