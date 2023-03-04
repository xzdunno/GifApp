package com.example.gifapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifapp.R
import com.example.gifapp.data.model.DBModelGif
import com.example.gifapp.databinding.MainItemRowBinding


class MainAdapter : RecyclerView.Adapter<CustomViewHolder>() {
    private lateinit var bind: MainItemRowBinding
    private var listData: List<DBModelGif>? = null
    fun setListData(list: List<DBModelGif>?) {
        listData = list
    }

    override fun getItemCount(): Int {
        return if (listData?.size != null) {
            listData?.size!!
        } else 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        bind = MainItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val cur = listData!![position]
        holder.bind(cur)
        holder.binding.gifPicRow.setOnClickListener {
            val bundle = bundleOf(
                "id" to cur.id
            )
            it.findNavController().navigate(R.id.gifCardFragment, bundle)
        }
    }
}

class CustomViewHolder(val binding: MainItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gif: DBModelGif) {
        Glide.with(binding.root.context).load(gif.url).into(binding.gifPicRow)
    }
}