package com.bitc.testapp

import android.content.DialogInterface
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitc.testapp.adapter.PlacesAdapter
import com.bitc.testapp.databinding.ActivityMainBinding
import com.bitc.testapp.fragment.*
import com.bitc.testapp.model.PlaceModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    companion object {
        val placeList = arrayListOf<PlaceModel>()
    }

    private lateinit var mAdapter: PlacesAdapter

//    class FragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
//        val fragments: List<Fragment>
//
//        init {
//            fragments = listOf(WalkFragment(),PetWalkFragment(), RunningFragment(), RidingFragment(),DriveFragment(), MapFragment())
//        }
//
//        override fun getItemCount(): Int {
//            return fragments.size
//        }
//
//        override fun createFragment(position: Int): Fragment {
//            return fragments[position]
//        }
//    }

    override fun onBackPressed() {
        Toast.makeText(this, "로그인 페이지로 돌아갑니다", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        setSupportActionBar(binding.toolbar) // 드로어 출력 버튼

        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = FragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager){tab, position ->
            when(position){
                0 -> tab.setText("걷기")
                1 -> tab.setText("펫산책")
                2 -> tab.setText("러닝")
                3 -> tab.setText("라이딩")
                4 -> tab.setText("드라이브")
                5 -> tab.setText("추천장소")
            }
        }.attach()

        binding.fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        binding.fabForRecPlace.setOnClickListener {
            val intent_recPlace = Intent(this, RecommendActivity::class.java)
            startActivity(intent_recPlace)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            var item3 = findViewById<View>(R.id.drawerSearch)
            var item4 = findViewById<View>(R.id.drawerLogout)
            var item5 = findViewById<View>(R.id.drawerQuit)
            
            item3.setOnClickListener {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            item4.setOnClickListener {
                TestApplication.auth.signOut()
                TestApplication.email = null
                Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            item5.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                    .setTitle("탈퇴 확인")
                    .setMessage("정말 탈퇴하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네", DialogInterface.OnClickListener{dialogInterface, which ->
                        TestApplication.auth.currentUser?.delete()
                        TestApplication.auth.signOut()
                        TestApplication.email = null
                        Toast.makeText(this, "정상적으로 탈퇴했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    })
                    .setNegativeButton("아니오", DialogInterface.OnClickListener{dialogInterface, which ->
                        Log.d("myLog", "cancel")
                    })
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setSupportActionBar(binding.toolbar) // 드로어 출력 버튼

        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = FragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager){tab, position ->
            when(position){
                0 -> tab.setText("걷기")
                1 -> tab.setText("펫산책")
                2 -> tab.setText("러닝")
                3 -> tab.setText("라이딩")
                4 -> tab.setText("드라이브")
                5 -> tab.setText("추천장소")
            }
        }.attach()

        binding.fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        binding.fabForRecPlace.setOnClickListener {
            val intent_recPlace = Intent(this, RecommendActivity::class.java)
            startActivity(intent_recPlace)
        }

    }

    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged()
    }

    private fun setAdapter() {
        mAdapter = PlacesAdapter(placeList)
        binding.rvPlace.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
}