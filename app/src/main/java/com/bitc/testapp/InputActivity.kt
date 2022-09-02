package com.bitc.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import com.bitc.testapp.databinding.ActivityInputBinding
import com.bitc.testapp.model.PlaceModel
import com.bitc.testapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var radioResult: String

        binding.saveBtn.setOnClickListener {
            if (binding.walk.isChecked){
                radioResult = binding.walk.text.toString()
            }else if(binding.withPet.isChecked){
                radioResult = binding.withPet.text.toString()
            }else if(binding.running.isChecked){
                radioResult = binding.running.text.toString()
            }else if(binding.riding.isChecked){
                radioResult = binding.riding.text.toString()
            }else{
                radioResult = binding.drive.text.toString()
            }

            var placeModel = PlaceModel(
                id = 0,
                placeName = binding.etPlaceName.text.toString(),
                purpose = radioResult,
                city = binding.etCity.text.toString(),
                address = binding.etAddress.text.toString(),
                description = binding.etDescription.text.toString()
            )
            val networkService = TestApplication.networkService
            val placeInsertCall = networkService.insert(placeModel)
            placeInsertCall.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("myLog", response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    call.cancel()
                }
            })
            finish()
        }

//        binding.saveBtn.setOnClickListener {
//            var userModel = UserModel(
//                id = 0,
//                username = binding.etUsername.text.toString(),
//                name = binding.etName.text.toString(),
//                password = binding.etPassword.text.toString(),
//                tel = binding.etTel.text.toString(),
//                roles = binding.etRole.text.toString()
//            )
//            val networkService = (applicationContext as TestApplication).networkService
//            val userInsertCall = networkService.insert(userModel)
//            userInsertCall.enqueue(object : Callback<String>{
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    Log.d("myLog", response.body().toString())
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    call.cancel()
//                }
//            })
//            finish()
//        }

        binding.cancelBtn.setOnClickListener {
            onBackPressed()
        }
    }
}