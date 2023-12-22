package com.capstone.tourbuddyapp.ui.main.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tourbuddyapp.databinding.ItemRowDestinationBinding
import com.capstone.tourbuddyapp.model.Destination

class DestinationAdapter : ListAdapter<Destination, DestinationAdapter.ListViewHolder>(DIFF_CALLBACK) {

    // mendeklarasikan variabel onItemClick untuk berpindah halaman ke detail story
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationAdapter.ListViewHolder {
        val binding = ItemRowDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationAdapter.ListViewHolder, position: Int) {
        val listDestinationItem = getItem(position)
        holder.bind(listDestinationItem, onItemClickCallback)
    }

    inner class ListViewHolder(private val binding: ItemRowDestinationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listDestinationItem: Destination, onItemClickCallback: OnItemClickCallback?) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(listDestinationItem)
            }
            // binding utk menampilkan gambar, content wisata, deskripsi, lokasi di hal utama
            binding.tvNameDestination.text = listDestinationItem.name
            binding.tvItemLocation.text = listDestinationItem.locationName
            Glide.with(binding.root.context)
                .load(listDestinationItem.photo)
                .into(binding.ivDestination)

        }
    }

    // membuat interface untuk dapat berpindah halaman detail story
    interface OnItemClickCallback{
        fun onItemClicked(data: Destination)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Destination>() {
            override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
                return oldItem == newItem
            }
        }
    }
}