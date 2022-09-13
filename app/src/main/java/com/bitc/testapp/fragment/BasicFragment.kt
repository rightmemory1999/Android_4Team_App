package com.bitc.testapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.testapp.R
import com.bitc.testapp.TestApplication
import com.bitc.testapp.adapter.PlaceAdapter
import com.bitc.testapp.adapter.PlacesAdapter
import com.bitc.testapp.adapter.SearchAdapter
import com.bitc.testapp.databinding.FragmentBasicBinding
import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.PlaceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBasicBinding.inflate(inflater, container, false)

        val call: Call<PlaceListModel> = TestApplication.networkService.getPlaces()
        call.enqueue(object : Callback<PlaceListModel>{
            override fun onResponse(
                call: Call<PlaceListModel>,
                response: Response<PlaceListModel>
            ) {
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var adapter = PlacesAdapter(response.body()?.places as ArrayList<PlaceModel>)
//                var adapter = SearchAdapter(activity as Context, response.body()?.places)
                binding.recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
                call.cancel()
            }
        })
        return binding.root
    }
}