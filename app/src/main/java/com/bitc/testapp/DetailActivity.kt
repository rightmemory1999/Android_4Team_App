package com.bitc.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bitc.testapp.databinding.ActivityDetailBinding
import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.PlaceModel
import com.bitc.testapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val id = intent.getLongExtra("id", 0)

        val networkService = TestApplication.networkService
//        val networkService = (applicationContext as TestApplication).networkService

        val placeModelCall = networkService.getPlace(id)
        placeModelCall.enqueue(object : Callback<PlaceModel> {
            override fun onResponse(call: Call<PlaceModel>, response: Response<PlaceModel>) {
                val placeModel = response.body()
                binding.tvPlaceName.text = "${placeModel?.placeName}"
                binding.PlaceImg.clipToOutline = true
                binding.tvCity.text = "#${placeModel?.city} "
                binding.tvAddress.text = "#${placeModel?.address} "
                binding.tvPurpose.text = "#${placeModel?.purpose} "
                binding.tvDesc.text = "${placeModel?.description}"

                Log.d("myLog", "${placeModel?.username}")

                // 로그인 한 이메일 관리자 계정(여기서는 rightmemory@naver.com으로 설정)인 경우에만.. 게시물 상세 페이지에서 삭제 버튼 표시됨!
                // OR 조건으로 다른 계정도 추가 가능할 듯
                if (TestApplication.email == "rightmemory@naver.com") {
                    binding.deleteBtn.visibility = View.VISIBLE
                }

                binding.deleteBtn.setOnClickListener {
                    val placeDeleteCall = networkService.delete(placeModel!!)
                    placeDeleteCall.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("myLog", response.body().toString())
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            call.cancel()
                        }
                    })
                    finish()
                }

                if (TestApplication.email == placeModel?.username) {
                    binding.deleteBtn1.visibility = View.VISIBLE
                    binding.updateBtn.visibility = View.VISIBLE
                }

                binding.deleteBtn1.setOnClickListener {
                    val placeDeleteCall = networkService.delete(placeModel!!)
                    placeDeleteCall.enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("myLog", response.body().toString())
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            call.cancel()
                        }
                    })
                    finish()
                }

                binding.listBtn.setOnClickListener {
                    onBackPressed()
                }

/*                binding.listBtn.setOnClickListener{
                    val placeListCall = networkService.list(placeModel!!)
                    placeListCall.enqueue(object : Callback<String>{
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("myLog", response.body().toString())
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            call.cancel()
                        }
                    })
                    finish()
                }*/
            }

            override fun onFailure(call: Call<PlaceModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

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

        val buttonClick = findViewById<Button>(R.id.updateBtn)
        buttonClick.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("ID",id)
            startActivity(intent)
        }

    }
}