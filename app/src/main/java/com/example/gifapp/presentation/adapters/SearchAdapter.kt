package com.example.gifapp.presentation.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifapp.R
import com.example.gifapp.data.model.Gifs
import com.example.gifapp.databinding.MainItemRowBinding

class SearchAdapter : PagingDataAdapter<Gifs, SearchCustomViewHolder>(GifsDiffItemCallback) {
    private lateinit var bind: MainItemRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCustomViewHolder {
        bind = MainItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchCustomViewHolder(bind)
    }

    override fun onBindViewHolder(holder: SearchCustomViewHolder, position: Int) {
        val cur = getItem(position)
        holder.bind(cur!!)
        holder.binding.gifPicRow.setOnClickListener {
            val bundle = bundleOf(
                "id" to cur.id
            )
            it.findNavController().navigate(R.id.gifCardFragment, bundle)
        }
    }
}

class SearchCustomViewHolder(val binding: MainItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(gif: Gifs) {
        Glide.with(binding.root.context).load(gif.images.original.url)
            .placeholder(ColorDrawable(Color.TRANSPARENT))
            .into(binding.gifPicRow)
    }
}

private object GifsDiffItemCallback : DiffUtil.ItemCallback<Gifs>() {

    override fun areItemsTheSame(oldItem: Gifs, newItem: Gifs): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Gifs, newItem: Gifs): Boolean {
        return oldItem.images.original.url == newItem.images.original.url
    }
}