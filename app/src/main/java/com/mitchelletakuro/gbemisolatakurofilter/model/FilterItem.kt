package com.mitchelletakuro.gbemisolatakurofilter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterItem(
    var colors: List<String>,
    val countries: List<String>,
    val end_year: Int,
    var gender: String,
    val id: Int,
    val start_year: Int
): Parcelable