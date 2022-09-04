package com.bitc.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitc.testapp.adapter.TestAdapter
import com.bitc.testapp.databinding.ActivityMainBinding
import com.bitc.testapp.fragment.*
import com.bitc.testapp.model.UserListModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    class FragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(BasicFragment(), PetWalkFragment(), RunningFragment(), RidingFragment(), DriveFragment(), MapFragment())
        }
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this, "로그인 페이지로 돌아갑니다", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
    }
}