package com.bitc.testapp

import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bitc.testapp.databinding.ActivityDetailBinding
import com.bitc.testapp.model.PlaceModel
import com.google.android.material.internal.ContextUtils.getActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        var photoUri: Drawable?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("id", 0)
        val placeName = intent.getStringExtra("placeName")
        val purpose = intent.getStringExtra("purpose")
        val city = intent.getStringExtra("city")
        val address = intent.getStringExtra("address")
        val description = intent.getStringExtra("description")

        binding.tvPlaceName.text = placeName
        binding.tvPurpose.text = purpose
        binding.tvCity.text = city
        binding.tvAddress.text = address
        binding.tvDesc.text = description

        if(photoUri != null) {
            binding.PlaceImg.setImageDrawable(photoUri)
            photoUri = null
        }

        val networkService = TestApplication.networkService
//        val networkService = (applicationContext as TestApplication).networkService

//        val placeModelCall = networkService.getPlace(id)
//        placeModelCall.enqueue(object : Callback<PlaceModel>{
//            override fun onResponse(call: Call<PlaceModel>, response: Response<PlaceModel>) {
//                val placeModel = response.body()
//                binding.tvPlaceName.text = "${placeModel?.placeName}"
//                binding.PlaceImg.clipToOutline = true
//                binding.tvCity.text = "#${placeModel?.city} "
//                binding.tvAddress.text = "#${placeModel?.address} "
//                binding.tvPurpose.text = "#${placeModel?.purpose} "
//                binding.tvDesc.text = "${placeModel?.description}"
//            }
//
//            override fun onFailure(call: Call<PlaceModel>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

//        val userModelCall = networkService.doGetUser(id)
//        userModelCall.enqueue(object : Callback<UserModel>{
//            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//                val userModel = response.body()
//                binding.tvId.text = "${userModel?.id}"
//                binding.tvName.text = "${userModel?.name}"
//                binding.tvUsername.text = "${userModel?.username}"
//                binding.tvTel.text = "${userModel?.tel}"
//            }
//
//            override fun onFailure(call: Call<UserModel>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }
}