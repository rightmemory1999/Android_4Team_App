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

    //장소 등록
    @POST("insert")
    fun insert(@Body place: PlaceModel): Call<String>

    //장소 삭제
    @POST("delete")
    fun delete(@Body place: PlaceModel): Call<String>

    //목적별(걷기, 펫산책 등) 장소 목록 출력
    @GET("listBy/{purpose}")
    fun getPlacesByPurpose(@Path("purpose") purpose: String): Call<PlaceListModel>

    @GET("getPlace/{id}")
    fun getPlace(@Path("id") id: Long): Call<PlaceModel>

//    @POST("insertPlace")
//    fun insertPlace(@Body place: PlaceModel): Call<String>


}