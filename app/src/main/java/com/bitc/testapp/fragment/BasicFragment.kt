package com.bitc.testapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.testapp.R
import com.bitc.testapp.TestApplication
import com.bitc.testapp.adapter.PlaceAdapter
import com.bitc.testapp.databinding.FragmentBasicBinding
import com.bitc.testapp.model.PlaceListModel
import com.bitc.testapp.model.UserListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBasicBinding.inflate(inflater, container, false)

        val call: Call<PlaceListModel> = TestApplication.networkService.getPlacesByPurpose("걷기")
        call.enqueue(object : Callback<PlaceListModel>{
            override fun onResponse(
                call: Call<PlaceListModel>,
                response: Response<PlaceListModel>
            ) {
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var adapter = PlaceAdapter(activity as Context, response.body()?.places)
                binding.recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
                call.cancel()
            }
        })


//        val networkService = TestApplication.networkService
//        val placeListModelCall = networkService.getPlaces()
//        placeListModelCall.enqueue(object : Callback<PlaceListModel>{
//            override fun onResponse(
//                call: Call<PlaceListModel>,
//                response: Response<PlaceListModel>
//            ) {
//                if(response.isSuccessful){
//                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//                    var adapter = PlaceAdapter(activity as Context, response.body()?.places)
//                    binding.recyclerView.adapter = adapter
//                    binding.recyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
//
//                }
//            }
//
//            override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
//                call.cancel()
//            }
//
//        })

        return binding.root
    }

//    override fun onStart() {
//        super.onStart()
//        val binding = FragmentBasicBinding.inflate(layoutInflater)
//        var purpose: String? = this.arguments?.get("purpose").toString()
//        val networkService = TestApplication.networkService
//        val placeListModelCall = networkService.getPlaces(purpose)
//        placeListModelCall.enqueue(object : Callback<PlaceListModel>{
//            override fun onResponse(
//                call: Call<PlaceListModel>,
//                response: Response<PlaceListModel>
//            ) {
//                if(response.isSuccessful){
//                    binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//                    var adapter = PlaceAdapter(activity as Context, response.body()?.places)
//                    binding.recyclerView.adapter = adapter
//
//                }
//            }
//
//            override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
//                call.cancel()
//            }
//
//        })
//    }
}