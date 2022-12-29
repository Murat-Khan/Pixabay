package com.murat.pixabay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.murat.pixabay.databinding.PixaItemBinding

class PixaAdapter() : RecyclerView.Adapter<PixaAdapter.PixaViewHolder>() {

    var list = ArrayList<ImageModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        return PixaViewHolder(
            PixaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false))
    }
    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.onBind(list[position])
    }
    fun fillList(list : ArrayList<ImageModel>, isClear : Boolean = false){
        if (isClear){
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }else{
            this.list.addAll(list)
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int = list.size

    class PixaViewHolder(private var binding : PixaItemBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(model: ImageModel) {
            binding.imageView.load(model.largeImageURL)
        }

    }


}