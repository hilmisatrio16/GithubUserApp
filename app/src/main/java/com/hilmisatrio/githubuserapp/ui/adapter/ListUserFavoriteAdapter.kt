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
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.databinding.ItemFollowBinding
import com.hilmisatrio.githubuserapp.utils.ConstantValue.USERNAME
import de.hdodenhof.circleimageview.CircleImageView

class ListUserFavoriteAdapter : RecyclerView.Adapter<ListUserFavoriteAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<UserFavorite>() {
        override fun areItemsTheSame(
            oldItem: UserFavorite,
            newItem: UserFavorite
        ): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(
            oldItem: UserFavorite,
            newItem: UserFavorite
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    var differ = AsyncListDiffer(this, diffCallbackUser)
    fun submitData(valueList: ArrayList<UserFavorite>) {
        differ.submitList(valueList)
    }

    class ViewHolder(var binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataUser = differ.currentList[position]
        with(holder.binding) {
            imageUser.loadImageUser(dataUser.urlImageAvatar)
            tvUsername.text = dataUser.username
            tvLinkGithub.text = dataUser.linkGithub
        }

        holder.itemView.setOnClickListener {
            val setBundle = Bundle().apply {
                putString(USERNAME, dataUser.username)
            }
            it.findNavController()
                .navigate(R.id.action_favoriteFragment_to_detailFragment, setBundle)
        }

    }

    private fun CircleImageView.loadImageUser(urlImage: String?) {
        Glide.with(this.context).load(urlImage).into(this)
    }
}