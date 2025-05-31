package com.matchmate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matchmate.data.model.User
import com.matchmate.databinding.ItemMatchCardBinding

class MatchAdapter(val listener: MatchClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users: List<User> = emptyList()

    inner class ViewHolder(val binding: ItemMatchCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setUsers(newUsers: List<User>) {
        users = newUsers
        println(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMatchCardBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as ViewHolder
        val user = users[position]

        viewHolder.binding.apply {
            tvName.text = user.name?.first + " " + user.name?.last
            tvLocation.text = user.location?.city + ", " + user.location?.state

            Glide.with(holder.itemView.context)
                .load(user.picture?.large)
                .into(ivUser)

            if (user.isAccepted) {
                btnAccept.isEnabled = false
                btnDecline.visibility = View.GONE
            } else {
                btnAccept.isEnabled = true
                btnDecline.visibility = View.VISIBLE
            }

            if (user.isDeclined) {
                btnAccept.visibility = View.GONE
                btnDecline.isEnabled = false
            } else {
                btnAccept.visibility = View.VISIBLE
                btnDecline.isEnabled = true
            }

            btnAccept.setOnClickListener {
                listener.onAcceptClicked(user)
            }

            btnDecline.setOnClickListener {
                listener.onDeclineClicked(user)
            }
        }
    }

    override fun getItemCount() = users.size

    interface MatchClickListener {
        fun onAcceptClicked(user: User)
        fun onDeclineClicked(user: User)
    }
}