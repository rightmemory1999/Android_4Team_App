package com.bitc.testapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitc.testapp.R
import com.bitc.testapp.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.intellij.lang.annotations.Language

class MapFragment : Fragment() {
    lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        val title = arrayOf("좋은산책로", "추천라이딩코스", "반려견과여기로")
        val latitudes = arrayOf(35.21905100212, 35.120355462733, 35.20154247887)
        val longitudes = arrayOf(128.84794069597, 129.11201397379, 129.10588031253)

        for (i in title.indices){
            var location = LatLng(latitudes[i], longitudes[i])
            mMap.addMarker(MarkerOptions().position(location).title(title[i]))
        }

        val basicMarker = LatLng(35.22500769737,128.98331920464)
        mMap.addMarker(MarkerOptions().position(basicMarker).title("베스트러닝코스")) //마크찍기
        mMap.moveCamera(CameraUpdateFactory.newLatLng(basicMarker)) //카메라 옮기기
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(basicMarker, 10.0f)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
//        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}