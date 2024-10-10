package com.example.icarus

data class BirdLocation(
    val speciesCode: String,
    val comName: String,
    val lat: Double,
    val lng: Double,
    val locName: String,
    val dist: Int
)
