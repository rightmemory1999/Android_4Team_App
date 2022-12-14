package com.bitc.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.provider.MediaStore
import com.bitc.testapp.databinding.ActivityDetailBinding
import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.PlaceModel
import com.google.android.material.internal.ContextUtils.getActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    companion object {
        var photoUri: Drawable?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val id = intent.getLongExtra("id", 0)
        //toPost - 아래줄부터 photoUri 까지, onCreat() 안에 작성해놓음
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

                //게시물 게시자(username)가 같은 경우에만 수정, 삭제 가능하도록. 이외에 사람은 수정, 삭제 버튼이 보이지 않도록 설정.
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

                //목록 버튼 클릭시 그 전 화면으로 넘어가도록 설정.
                binding.listBtn.setOnClickListener {
                    onBackPressed()
                }
            }

            override fun onFailure(call: Call<PlaceModel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        // 수정하기 버튼 클릭시 UpdateActivity로 가도록.
        val buttonClick = findViewById<Button>(R.id.updateBtn)
        buttonClick.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("ID",id)
            startActivity(intent)
        }
    }
}