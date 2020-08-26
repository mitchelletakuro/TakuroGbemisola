package com.mitchelletakuro.gbemisolatakurofilter.model

data class CarOwnerItem(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val country: String,
    val car_model: String,
    val car_model_year: Int,
    val car_color: String,
    val gender: String,
    val job_title: String,
    val bio: String
)