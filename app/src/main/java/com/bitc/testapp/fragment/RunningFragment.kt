package com.bitc.testapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.testapp.R
import com.bitc.testapp.TestApplication
import com.bitc.testapp.adapter.PlaceAdapter
import com.bitc.testapp.databinding.FragmentBasicBinding
import com.bitc.testapp.model.PlaceListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RunningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBasicBinding.inflate(inflater, container, false)

        val networkService = TestApplication.networkService
        val placeListModelCall = networkService.getPlacesByPurpose("러닝")
        placeListModelCall.enqueue(object : Callback<PlaceListModel> {
            override fun onResponse(
                call: Call<PlaceListModel>,
                response: Response<PlaceListModel>
            ) {
                if(response.isSuccessful){
                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                    var adapter = PlaceAdapter(activity as Context, response.body()?.places)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
                call.cancel()
            }
        })
        return binding.root
    }
}