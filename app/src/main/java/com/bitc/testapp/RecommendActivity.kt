package com.bitc.testapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import com.bitc.testapp.databinding.ActivityRecommendBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RecommendActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityRecommendBinding
    lateinit var mMap: GoogleMap
    lateinit var manager: LocationManager
    lateinit var customMarker: BitmapDescriptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        customMarker = BitmapDescriptorFactory.fromResource(R.drawable.redflag)

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){
            if(it){
                Log.d("myLog", "granted..")
            }else {
                Log.d("myLog", "denied..")
            }
        }

        if(ContextCompat.checkSelfPermission(
                this,
            "android.permission.ACCESS_FINE_LOCATION")==PackageManager.PERMISSION_GRANTED
        ){
            Log.d("myLog", "granted..")
        } else {
            permissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.recMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val title = arrayOf("을숙도산책로", "대저 자전거 라이딩", "댕댕이가 좋아하는 공원", "이기대 러닝코스")
        val latitudes = arrayOf(35.0995441147, 35.120355462733, 35.20154247887, 35.22500769737)
        val longitudes = arrayOf(128.93991106, 129.11201397379, 129.10588031253, 128.98331920464)

        for (i in title.indices){
            var location = LatLng(latitudes[i], longitudes[i])
            mMap.addMarker(MarkerOptions().position(location).title(title[i]).icon(customMarker))
        }

        val basicLocation = LatLng(35.1795543, 129.0756416)
        mMap.addMarker(MarkerOptions().position(basicLocation).title("기본 위치"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(basicLocation, 11f))

//        val place1 = LatLng(35.1795543, 129.0756416)
//        mMap.addMarker(MarkerOptions().position(place1).title("좋은장소").icon(customMarker))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, 11f))
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        val listner = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                val currentLat = p0.latitude
                val currentLng = p0.longitude
                mMap.clear()

                val title = arrayOf("을숙도산책로", "대저 자전거 라이딩", "댕댕이가 좋아하는 공원", "이기대 러닝코스")
                val latitudes = arrayOf(35.0995441147, 35.120355462733, 35.20154247887, 35.22500769737)
                val longitudes = arrayOf(128.93991106, 129.11201397379, 129.10588031253, 128.98331920464)

                for (i in title.indices){
                    var location = LatLng(latitudes[i], longitudes[i])
                    mMap.addMarker(MarkerOptions().position(location).title(title[i]).icon(customMarker))
                }

                val currentLocation = LatLng(currentLat, currentLng)
                mMap.addMarker(MarkerOptions()
                    .position(currentLocation)
                    .title("내 위치")
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 11f))
            }
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 11f, listner)
    }
}