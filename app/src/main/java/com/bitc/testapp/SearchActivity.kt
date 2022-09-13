package com.bitc.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitc.testapp.adapter.PlaceAdapter
import com.bitc.testapp.adapter.SearchAdapter
import com.bitc.testapp.databinding.ActivitySearchBinding
import com.bitc.testapp.fragment.*
import com.bitc.testapp.model.PlaceModel
import java.util.Locale.filter


class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var searchAdapter: SearchAdapter

    class FragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(BasicFragment())
        }
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = FragmentAdapter(this)
        binding.viewPagerPlaces.adapter = adapter

        searchAdapter = SearchAdapter(this, ArrayList<PlaceModel>())

        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchAdapter.filter.filter(query)
                    Log.d("myLog", "TextSubmit: ${query}")
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchAdapter.filter.filter(query)
                    Log.d("myLog", "TextChange: ${query}")
                    return false
                }
            }
        binding.searchViewPlaces.setOnQueryTextListener(searchViewTextListener)
    }
}