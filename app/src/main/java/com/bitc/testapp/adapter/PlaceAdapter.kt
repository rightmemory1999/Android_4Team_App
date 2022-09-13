package com.bitc.testapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitc.testapp.DetailActivity
import com.bitc.testapp.databinding.ItemBinding
import com.bitc.testapp.model.PlaceModel

class PlaceViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

class PlaceAdapter(
    val context: Context, val places: MutableList<PlaceModel>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PlaceViewHolder).binding
        val place = places!![position]
        binding.tvPlaceName.text = place.placeName
        binding.tvCity.text = "#${place.city} "
        binding.tvPurpose.text = "#${place.purpose}"

        binding.mainArea.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", place?.id)
            intent.putExtra("placeName", place?.placeName)
            intent.putExtra("purpose", place?.purpose)
            intent.putExtra("city", place?.city)
            intent.putExtra("address", place?.address)
            intent.putExtra("description", place?.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return places?.size ?: 0
    }
}