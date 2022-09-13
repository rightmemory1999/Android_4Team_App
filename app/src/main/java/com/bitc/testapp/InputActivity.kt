package com.bitc.testapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bitc.testapp.databinding.ActivityInputBinding
import com.bitc.testapp.model.PlaceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    lateinit var binding:ActivityInputBinding

    companion object {
        const val DEFAULT_GALLERY_REQUEST_CODE = 1002
    }

    private var photoDrawbale: Drawable?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var radioResult: String

        binding.ivPhoto.setOnClickListener {
            startDefaultGalleryApp()
        }

        binding.cancelBtn.setOnClickListener {
            onBackPressed()
        }

        binding.saveBtn.setOnClickListener {
            if (binding.walk.isChecked) {
                radioResult = binding.walk.text.toString()
            } else if (binding.withPet.isChecked) {
                radioResult = binding.withPet.text.toString()
            } else if (binding.running.isChecked) {
                radioResult = binding.running.text.toString()
            } else if (binding.riding.isChecked) {
                radioResult = binding.riding.text.toString()
            } else {
                radioResult = binding.drive.text.toString()
            }
            Log.d("myLog", "${TestApplication.email}+확인")

            var placeModel = PlaceModel(
                id = 0,
                username = TestApplication.email.toString(),
                placeName = binding.etPlaceName.text.toString(),
                purpose = radioResult,
                city = binding.etCity.text.toString(),
                address = binding.etAddress.text.toString(),
                description = binding.etDescription.text.toString(),
                photoDrawable = photoDrawbale
            )
            val networkService = TestApplication.networkService
            val placeInsertCall = networkService.insert(placeModel)
            placeInsertCall.enqueue(object : Callback<String> {
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

    private fun startDefaultGalleryApp() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            DEFAULT_GALLERY_REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri

                binding.ivPhoto.setImageURI(uri)

                val inputStream = contentResolver.openInputStream(uri)
                val drawable = Drawable.createFromStream(inputStream, uri.toString())

                photoDrawbale = drawable
            }

            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

