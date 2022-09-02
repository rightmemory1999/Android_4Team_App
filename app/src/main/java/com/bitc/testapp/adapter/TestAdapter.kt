package com.bitc.testapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitc.testapp.DetailActivity
import com.bitc.testapp.databinding.ItemBinding
import com.bitc.testapp.model.UserListModel
import com.bitc.testapp.model.UserModel

class MyViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

class TestAdapter(
    var context: Context, val datas: List<UserModel>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val user = datas?.get(position)
        binding.tvPlaceName.text = user?.username
        binding.tvCity.text = user?.name
        binding.tvPurpose.text = user?.roles
        binding.root.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", user?.id)
            intent.putExtra("name", user?.name)
            intent.putExtra("username", user?.username)
            intent.putExtra("tel", user?.tel)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

}