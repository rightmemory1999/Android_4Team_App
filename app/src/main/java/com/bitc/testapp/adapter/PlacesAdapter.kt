package com.bitc.testapp.adapter

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitc.testapp.DetailActivity
import com.bitc.testapp.databinding.ItemBinding
import com.bitc.testapp.model.PlaceModel
import kotlin.collections.ArrayList

class PlacesAdapter(
    private val dataList: ArrayList<PlaceModel>
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceModel) {
            binding.tvPlaceName.text = item.placeName
            binding.tvCity.text = "#${item.city} "
            binding.tvPurpose.text = "#${item.purpose}"

            if(item.photoDrawable != null) {
                binding.ivPhoto.setImageDrawable(item.photoDrawable!!)
            }

            binding.mainArea.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("placeName", item.placeName)
                intent.putExtra("purpose", item.purpose)
                intent.putExtra("city", item.city)
                intent.putExtra("address", item.address)
                intent.putExtra("description", item.description)

                DetailActivity.photoUri = item.photoDrawable

                binding.root.context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }
}