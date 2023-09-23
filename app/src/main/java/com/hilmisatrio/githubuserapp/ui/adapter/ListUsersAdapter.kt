package com.hilmisatrio.githubuserapp.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.remote.response.ItemUser
import com.hilmisatrio.githubuserapp.databinding.ItemUserBinding
import com.hilmisatrio.githubuserapp.utils.ConstantValue.USERNAME
import de.hdodenhof.circleimageview.CircleImageView

class ListUsersAdapter : RecyclerView.Adapter<ListUsersAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<ItemUser>() {
        override fun areItemsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: ItemUser, newItem: ItemUser): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    var differ = AsyncListDiffer(this, diffCallbackUser)
    fun submitData(valueList: ArrayList<ItemUser>) {
        differ.submitList(valueList)
    }

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataUser = differ.currentList[position]
        with(holder.binding) {
            imageUser.loadImageUser(dataUser.avatarUrl)
            tvUsername.text = dataUser.login
            tvScore.text = "Score ${dataUser.score}"
        }

        holder.itemView.setOnClickListener {
            val setBundle = Bundle().apply {
                putString(USERNAME, dataUser.login)
            }
            it.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, setBundle)
        }

    }

    private fun CircleImageView.loadImageUser(urlImage: String?) {
        Glide.with(this.context).load(urlImage).into(this)
    }
}