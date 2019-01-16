package com.example.googleplacesautocomplete.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.googleplacesautocomplete.R
import com.example.googleplacesautocomplete.db.HistoryEntity
import kotlinx.android.synthetic.main.item_history.view.*


class HistoryAdapter(
    private val context: Context,
    var items: MutableList<HistoryEntity>,
    var onClick: (position: Int) -> Unit,
    var onLongClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder.create(context, parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].apply {
            (holder as HistoryViewHolder).let { it ->
                it.name.text = name
                it.address.text = address
                it.itemView.apply {
                    startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
                    setOnClickListener {
                        onClick(position)
                    }
                    setOnLongClickListener {
                        onLongClick(position)
                        true
                    }
                }
            }
        }
    }

    companion object {
        class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name = view.text_name!!
            val address = view.text_address!!

            companion object {
                fun create(context: Context, parent: ViewGroup) =
                    HistoryViewHolder(inflateLayout(context, R.layout.item_history, parent))
            }
        }

        private fun inflateLayout(context: Context, layoutId: Int, parent: ViewGroup) =
            LayoutInflater.from(context).inflate(layoutId, parent, false)
    }
}