package com.bitc.testapp

import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.PlaceModel
import retrofit2.Call
import retrofit2.http.*

interface INetworkService {

    @GET("list")
    fun getPlaces(): Call<PlaceListModel>

    //장소 등록
    @POST("insert")
    fun insert(@Body place: PlaceModel): Call<String>

    //장소 삭제
    //DELETE, PUT의 경우, @POST("delete")으로 쓸 필요 없이 @DELETE @PUT 그대로 써도 됨
    @POST("delete")
    fun delete(@Body place: PlaceModel): Call<String>

    //장소 수정
    @POST("update")
    fun update(@Body place: PlaceModel): Call<String>

    @POST("list")
    fun list(@Body place: PlaceModel): Call<String>

    //목적별(걷기, 펫산책 등) 장소 목록 출력
    @GET("listBy/{purpose}")
    fun getPlacesByPurpose(@Path("purpose") purpose: String): Call<PlaceListModel>

    @GET("getPlace/{id}")
    fun getPlace(@Path("id") id: Long): Call<PlaceModel>

}