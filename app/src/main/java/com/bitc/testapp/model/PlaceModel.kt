package com.bitc.testapp.model

data class PlaceModel(
    var id: Long,
    //username을 통해 게시자가 같을 경우만 수정, 삭제가 가능하도록 설정.
    var username: String,
    var placeName: String,
    var purpose: String,
    var city: String,
    var address: String,
    var description: String
)
