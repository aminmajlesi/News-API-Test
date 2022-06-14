package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Message
import kotlinx.android.synthetic.main.item_message_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MessageViewHolder>(){

    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(message.image).into(ivMessageImage)
            tvID.text = message.id
            tvTitle.text = message.title
            tvDescription.text = message.description
            //tvRead.text = message.unread
            setOnClickListener {
                onItemClickListener?.let { it(message) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Message) -> Unit)? = null

    fun setOnItemClickListener(listener: (Message) -> Unit) {
        onItemClickListener = listener
    }

}