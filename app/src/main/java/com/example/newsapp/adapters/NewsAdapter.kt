package com.example.newsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Message
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.MessageDiffUtil
import kotlinx.android.synthetic.main.item_message_preview.view.*
/**
 * @author by Amin Majlesi
 */
class NewsAdapter(
    val onSaveClick : (Message) -> Unit,
) : RecyclerView.Adapter<NewsAdapter.MessageViewHolder>(){

    var isLongTouch : Boolean = false
    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    lateinit var viewModel: NewsViewModel

    private val differCallback = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            //return oldItem.id == newItem.id
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            //return oldItem == newItem
            return oldItem.description == newItem.description
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

            ivMessageImage.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            constraintExpand.visibility = View.GONE

            Glide.with(this).load(message.image).into(ivMessageImage)
            //tvID.text = message.id
            tvTitle.text = message.title
            tvDescription.text = message.description
            //tvRead.text = message.unread

            Glide.with(this).load(message.image).into(imageExpand)
            textViewExpand.text = message.description

            ivSaved.setOnClickListener {
                onSaveClick(message)
                onItemClickListener?.let { it(message) }
                Toast.makeText(context , "Message saved",Toast.LENGTH_LONG).show()
                ivSaved.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmark))

                //viewModel.updateBookMarked( true,message.id)

            }

            ivShared.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, "share")
                shareIntent.type = "text/plain"
                context.startActivity(Intent.createChooser(shareIntent,"send to"))
            }


            if(message.unread)
            {
                constraintItem.setBackgroundColor(resources.getColor(R.color.white))
            }
            else
            {
                constraintItem.setBackgroundColor(resources.getColor(R.color.colorMessageUnread))
            }

            ivMore.setOnClickListener {
                onItemClickListener?.let { it(message) }

                ivMore.setRotationX(180f)

                if(ivMessageImage.isVisible)
                {
                    ivMessageImage.visibility = View.GONE
                    tvDescription.visibility = View.GONE
                    constraintExpand.visibility = View.VISIBLE
                }
                else
                {
                    ivMessageImage.visibility = View.VISIBLE
                    tvDescription.visibility = View.VISIBLE
                    constraintExpand.visibility = View.GONE
                }


            }

            holder.itemView.setOnLongClickListener {
                onItemLongClickListener?.let { it(message) }
                isLongTouch = true
                cbDelete.isChecked = true

                true
            }

            if(isLongTouch)
            {
                cbDelete.visibility = View.VISIBLE

            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Message) -> Unit)? = null
    private var onItemLongClickListener: ((Message) -> Unit)? = null

    fun setOnItemClickListener(listener: (Message) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (Message) -> Unit) {
        onItemLongClickListener = listener
    }


}