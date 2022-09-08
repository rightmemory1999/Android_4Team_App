package com.bitc.testapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bitc.testapp.DetailActivity
import com.bitc.testapp.databinding.ItemBinding
import com.bitc.testapp.model.PlaceModel

class FilteredPlaceViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

class SearchAdapter(
    val context: Context, val places: MutableList<PlaceModel>?
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var filteredPlaces = ArrayList<PlaceModel>()
    var placeFilter = PlaceFilter()

    init {
        if (places != null) {
            filteredPlaces.addAll(places)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilteredPlaceViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as FilteredPlaceViewHolder).binding
        val place = filteredPlaces[position]
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
        return filteredPlaces.size
    }

    override fun getFilter(): Filter {
        return placeFilter
    }

    inner class PlaceFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            val filteredList: ArrayList<PlaceModel> = ArrayList<PlaceModel>()

            if(filterString.trim(' ').isEmpty()){
                results.values = places
                results.count = places!!.size

                return results

            } else if (filterString.trim{ it <= ' ' }.length <= 2) {
                if (places != null) {
                    for(place in places){
                        if(place.placeName.contains(filterString)){
                            filteredList.add(place)
                        }
                    }
                }

            } else {
                if (places != null) {
                    for(place in places){
                        if(place.placeName.contains(filterString)){
                            filteredList.add(place)
                        }
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredPlaces.clear()
            filteredPlaces.addAll(filterResults.values as ArrayList<PlaceModel>)
            notifyDataSetChanged()
        }
    }
}