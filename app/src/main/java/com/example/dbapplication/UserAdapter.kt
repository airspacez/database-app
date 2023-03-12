package com.example.dbapplication

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dbapplication.DAO.UserDao
import com.example.dbapplication.Entity.User

class UserAdapter(private var users: List<User>, private val userDao: UserDao) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClick(id: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val buttonDelete: Button = itemView.findViewById(R.id.delete)
    }

    private var listener: OnDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.textViewName.text = user.secondName + " " + user.firstname
        holder.textViewEmail.text = user.email

        holder.buttonDelete.setOnClickListener {
            listener?.onDeleteClick(user.id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }


    override fun getItemCount() = users.size
}