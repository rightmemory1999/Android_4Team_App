package com.bitc.testapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.testapp.adapter.PlacesAdapter
import com.bitc.testapp.adapter.TestAdapter
import com.bitc.testapp.databinding.ActivityMainBinding
import com.bitc.testapp.fragment.*
import com.bitc.testapp.model.PlaceModel
import com.bitc.testapp.model.UserListModel
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

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.open,
            R.string.close
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = FragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.setText("걷기")
                1 -> tab.setText("펫산책")
                2 -> tab.setText("러닝")
                3 -> tab.setText("라이딩")
                4 -> tab.setText("드라이브")
            }
        }.attach()

        binding.fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }


//        val networkService = (applicationContext as TestApplication).networkService
//        val networkService = TestApplication.networkService
//        val userListModelCall = networkService.doGetUserList()
//        userListModelCall.enqueue(object : Callback<UserListModel>{
//            override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {
//                if(response.isSuccessful){
//                    binding.recyclerView1.layoutManager = LinearLayoutManager(this@MainActivity)
//                    binding.recyclerView1.adapter = TestAdapter(this@MainActivity, response.body()?.users)
//                    binding.recyclerView1.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
//                }
//            }
//
//            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
//                call.cancel()
//            }
//        })
    }

//    override fun onStart() {
//        super.onStart()
////        val networkService = (applicationContext as TestApplication).networkService
//        val networkService = TestApplication.networkService
//        val userListModelCall = networkService.doGetUserList()
//        userListModelCall.enqueue(object : Callback<UserListModel>{
//            override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {
//                if(response.isSuccessful){
//                    binding.recyclerView1.layoutManager = LinearLayoutManager(this@MainActivity)
//                    binding.recyclerView1.adapter = TestAdapter(this@MainActivity, response.body()?.users)
//                    binding.recyclerView1.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
//                }
//            }
//
//            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
//                call.cancel()
//            }
//        })
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        setSupportActionBar(binding.toolbar) // 드로어 출력 버튼

        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = FragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.setText("걷기")
                1 -> tab.setText("펫산책")
                2 -> tab.setText("러닝")
                3 -> tab.setText("라이딩")
                4 -> tab.setText("드라이브")
            }
        }.attach()

        binding.fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
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