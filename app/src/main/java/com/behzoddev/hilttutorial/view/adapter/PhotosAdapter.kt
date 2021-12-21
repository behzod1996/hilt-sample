package com.behzoddev.hilttutorial.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.behzoddev.hilttutorial.common.loadUrl
import com.behzoddev.hilttutorial.data.Photo
import com.behzoddev.hilttutorial.databinding.ItemPhotosBinding

typealias ListItemClickListener<T> = (T) -> Unit

class PhotosAdapter(
    private val itemClickListener: ListItemClickListener<Photo>
) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    inner class PhotosViewHolder(val binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val differCallBack = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(ItemPhotosBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.ivPhoto.loadUrl(item.images[0].httpsUrl)
        holder.itemView.setOnClickListener { itemClickListener(item) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}