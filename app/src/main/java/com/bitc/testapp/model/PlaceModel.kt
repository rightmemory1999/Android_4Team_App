package com.bitc.testapp.model

import android.graphics.drawable.Drawable
import retrofit2.http.Url

data class PlaceModel(
    var id: Long,
    var username: String,
    var placeName: String,
    var purpose: String,
    var city: String,
    var address: String,
    var description: String,
    var photoDrawable: Drawable?
)
