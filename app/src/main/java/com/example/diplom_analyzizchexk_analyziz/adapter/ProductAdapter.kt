package com.example.diplom_analyzizchexk_analyziz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_analyzizchexk_analyziz.R
import com.example.diplom_analyzizchexk_analyziz.databinding.ListItemBinding
import com.example.diplom_analyzizchexk_analyziz.retrofit.Product
import androidx.recyclerview.widget.ListAdapter


class ProductAdapter : ListAdapter<Product, ProductAdapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding=ListItemBinding.bind(view)
        fun bind(product:Product)=with(binding){
            name.text="Магазин:"+product.shop
            date.text="Дата:"+product.date
            productView.text="Продукт:"+product.name_product
            sum.text=product.price_product.toString()+" рублей"
        }
    }
    class Comparator:DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.date.equals(newItem.date, true)

        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem==newItem
        }

    }
     override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):Holder{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder:Holder, position:Int){
        holder.bind(getItem(position))
    }


}



