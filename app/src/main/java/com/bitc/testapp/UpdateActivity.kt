package com.bitc.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import com.bitc.testapp.TestApplication.Companion.networkService
import com.bitc.testapp.databinding.ActivityUpdateBinding
import com.bitc.testapp.model.PlaceModel
import com.bitc.testapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id1 = intent.getLongExtra("ID", 0)
        Log.d("myLog", "aaa+${id1}")

            val networkService = TestApplication.networkService
//        val networkService = (applicationContext as TestApplication).networkService

            val placeModelCall = networkService.getPlace(id1)
            placeModelCall.enqueue(object : Callback<PlaceModel> {
                override fun onResponse(call: Call<PlaceModel>, response: Response<PlaceModel>) {
                    val placeModel = response.body()
                    binding.etPlaceName10.setText("${placeModel?.placeName}")

                    if (placeModel?.purpose == "걷기") {
                        binding.walk10.isChecked = true
                    } else if (placeModel?.purpose == "펫산책") {
                        binding.withPet10.isChecked = true
                    } else if (placeModel?.purpose == "러닝") {
                        binding.running10.isChecked = true
                    } else if (placeModel?.purpose == "라이딩") {
                        binding.riding10.isChecked = true
                    } else if (placeModel?.purpose == "드라이브") {
                        binding.drive10.isChecked = true
                    }

                    binding.etCity10.setText("${placeModel?.city}")
                    binding.etAddress10.setText("${placeModel?.address}")
                    binding.etDescription10.setText("${placeModel?.description}")

                    binding.cancelBtn10.setOnClickListener {
                        onBackPressed()
                    }
/*                    placeModel2= placeModel!!*/

                }

                override fun onFailure(call: Call<PlaceModel>, t: Throwable) {
                    Log.d("myLog", "aaa+${id1}")
                }

            })


        var radioResult: String
        binding.updateBtn10.setOnClickListener {
            if (binding.walk10.isChecked) {
                radioResult = binding.walk10.text.toString()
            } else if (binding.withPet10.isChecked) {
                radioResult = binding.withPet10.text.toString()
            } else if (binding.running10.isChecked) {
                radioResult = binding.running10.text.toString()
            } else if (binding.riding10.isChecked) {
                radioResult = binding.riding10.text.toString()
            } else {
                radioResult = binding.drive10.text.toString()
            }

            var placeModel = PlaceModel(
                id = id1,
                username = TestApplication.email.toString(),
                placeName = binding.etPlaceName10.text.toString(),
                purpose = radioResult,
                city = binding.etCity10.text.toString(),
                address = binding.etAddress10.text.toString(),
                description = binding.etDescription10.text.toString()
            )
            val networkService = TestApplication.networkService
            val placeUpdateCall = networkService.update(placeModel)
            placeUpdateCall.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("myLog", response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    call.cancel()
                }
            })
            finish()
        }
    }
}









