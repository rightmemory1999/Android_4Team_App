package com.bitc.testapp

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApplication: Application() {
    companion object{
        var networkService: INetworkService
        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://121.145.37.69:8084/base/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init {
            networkService = retrofit.create(INetworkService::class.java)
        }

        lateinit var auth: FirebaseAuth
        var email: String? = null
        fun checkAuth(): Boolean{
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email // 인증성공
                currentUser.isEmailVerified
            }?: let {
                false // 인증실패
            }
        }


    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
    }
}