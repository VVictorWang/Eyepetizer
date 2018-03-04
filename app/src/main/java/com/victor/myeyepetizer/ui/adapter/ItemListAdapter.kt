package com.victor.myeyepetizer.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.victor.myeyepetizer.R
import com.victor.myeyepetizer.bean.Item
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
class ItemListAdapter(var itemList: ArrayList<Item>) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {
    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        holder?.bind(itemList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent!!)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(items: ArrayList<Item>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(items: ArrayList<Item>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, null)) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Item) = with(itemView) {
            this.setOnClickListener {
                val intent = android.content.Intent(context, com.victor.myeyepetizer.ui.activity.DetailActivity::class.java)
                context.startActivity(intent)
            }
            Glide.with(context).load(item.data?.author?.icon).into(avatar)
            title.text = item.data?.title
            author.text = item.data?.author?.name
            if (item.tag != null) {
                tagView.text = item.tag
            }
            if (item.data?.tags != null && !item.data.tags.isEmpty()) {
                tagView.text = "#" + item.data.tags[0].name
            }
            Glide.with(context).load(item.data?.cover?.detail).into(cover)
        }
    }
}