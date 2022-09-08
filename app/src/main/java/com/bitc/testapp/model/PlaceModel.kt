package com.bitc.testapp.model

data class PlaceModel(
    var id: Long,
    var username: String,
    var placeName: String,
    var purpose: String,
    var city: String,
    var address: String,
    var description: String
)
