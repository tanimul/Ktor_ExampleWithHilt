package com.example.ktor_example_with_hilt.ui.adapter

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ktor_example_with_hilt.data.models.User
import com.example.ktor_example_with_hilt.databinding.LayoutRepoBinding

class UserViewAdapter(val context: Context, private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserViewAdapter.RecyclerViewHolder>() {

    private var listDataList: List<User> = ArrayList<User>()

    inner class RecyclerViewHolder(private val binding: LayoutRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val orientation = context.resources.configuration.orientation

        fun bind(datum: User) {
            with(datum) {
                with(binding)
                {
                    Glide.with(context).load(owner?.avatar_url).into(ivOwner)
                    tvTitle.text = name
                    tvDescription.text = description
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        tvType?.text = owner?.type
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(listDataList[position])

        holder.itemView.setOnClickListener {
            onItemClick.invoke(listDataList[position])
        }
    }

    fun setDataList(listDataList: List<User>) {
        this.listDataList = listDataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listDataList.size
    }
}