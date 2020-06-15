package com.sanket.mobileapp_poc2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanket.mobileapp_poc2.databinding.ItemUsersBinding
import com.sanket.mobileapp_poc2.model.Data

class DashboardAdapter(var data: ArrayList<Data>) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    fun addData(updatedData: List<Data>) {
        data.addAll(updatedData)
        notifyDataSetChanged()
    }

    fun reset(){
        data = ArrayList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUsersBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data = data[position])
    }
}