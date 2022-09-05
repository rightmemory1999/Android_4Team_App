package com.bitc.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bitc.testapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(TestApplication.checkAuth()){
            changeVisibility("login")
        }else {
            changeVisibility("logout")
        }

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리...........................
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                TestApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            TestApplication.email = account.email
                            changeVisibility("login")
                        }else {
                            changeVisibility("logout")
                        }
                    }
            }catch (e: ApiException){
                changeVisibility("logout")
            }
        }

        binding.logoutBtn.setOnClickListener {
            //로그아웃...........
            TestApplication.auth.signOut()
            TestApplication.email = null
            changeVisibility("logout")
        }

        binding.goSignInBtn.setOnClickListener{
            changeVisibility("signin")
        }

        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.signBtn.setOnClickListener {
            //이메일,비밀번호 회원가입........................
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            TestApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        TestApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener{ sendTask ->
                                if(sendTask.isSuccessful){
                                    Toast.makeText(baseContext,
                                        "회원가입 성공, 전송된 메일을 확인해주세요",
                                        Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }else {
                                    Toast.makeText(baseContext,
                                        "메일발송 실패",
                                        Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                            }
                    } else {
                        Toast.makeText(baseContext,
                            "회원가입실패",
                            Toast.LENGTH_SHORT).show()
                        changeVisibility("logout")
                    }
                }
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            TestApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        if(TestApplication.checkAuth()){
                            TestApplication.email = email
                            changeVisibility("login")
                            Toast.makeText(baseContext, "${TestApplication.email} 님 반갑습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        }else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()

                        }
                    }else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        binding.toMainBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var interval = tempTime - backPressedTime

        if(interval >= 0 && interval <= 2000) {
            TestApplication.auth.signOut()
            TestApplication.email = null
            super.onBackPressed()
        } else{
            backPressedTime = tempTime
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun changeVisibility(mode: String){
        if(mode === "login"){
            binding.run {
                authMainTextView.text = "${TestApplication.email} 님 반갑습니다."
                toMainBtn.visibility = View.VISIBLE
                logoutBtn.visibility= View.VISIBLE
                goSignInBtn.visibility= View.GONE
                googleLoginBtn.visibility= View.GONE
                signInText.visibility = View.GONE
                notYet.visibility = View.GONE
                authEmailEditView.visibility= View.GONE
                authPasswordEditView.visibility= View.GONE
                signBtn.visibility= View.GONE
                loginBtn.visibility= View.GONE
            }

        }else if(mode === "logout"){
            binding.run {
                authMainTextView.visibility = View.GONE
                toMainBtn.visibility = View.GONE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                googleLoginBtn.visibility = View.VISIBLE
                signInText.visibility = View.VISIBLE
                notYet.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            }
        }else if(mode === "signin"){
            binding.run {
                authMainTextView.visibility = View.GONE
                toMainBtn.visibility = View.GONE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                googleLoginBtn.visibility = View.GONE
                signInText.visibility = View.GONE
                notYet.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            }
        }
    }
}