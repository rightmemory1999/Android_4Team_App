package com.bitc.testapp

import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.PlaceModel
import com.bitc.testapp.model.UserListModel
import com.bitc.testapp.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface INetworkService {
//    @GET("userList")
//    fun doGetUserList(): Call<UserListModel>
//
//    @GET("getUser/{id}")
//    fun doGetUser(@Path("id") id: Long): Call<UserModel>
//
//    @POST("insertUser")
//    fun insertUser(@Body user: UserModel): Call<String>

    @GET("list")
    fun getPlaces(): Call<PlaceListModel>

    @POST("insert")
    fun insert(@Body place: PlaceModel): Call<String>

    @GET("listBy/{purpose}")
    fun getPlacesByPurpose(@Path("purpose") purpose: String): Call<PlaceListModel>

    @GET("getPlace/{id}")
    fun getPlace(@Path("id") id: Long): Call<PlaceModel>

//    @POST("insertPlace")
//    fun insertPlace(@Body place: PlaceModel): Call<String>


}