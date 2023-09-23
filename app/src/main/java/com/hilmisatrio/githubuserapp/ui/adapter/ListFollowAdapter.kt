package com.hilmisatrio.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseFollowItem
import com.hilmisatrio.githubuserapp.databinding.ItemFollowBinding
import de.hdodenhof.circleimageview.CircleImageView

class ListFollowAdapter : RecyclerView.Adapter<ListFollowAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<ResponseFollowItem>() {
        override fun areItemsTheSame(
            oldItem: ResponseFollowItem,
            newItem: ResponseFollowItem
        ): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(
            oldItem: ResponseFollowItem,
            newItem: ResponseFollowItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    var differ = AsyncListDiffer(this, diffCallbackUser)
    fun submitData(valueList: ArrayList<ResponseFollowItem>) {
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
            imageUser.loadImageUser(dataUser.avatarUrl)
            tvUsername.text = dataUser.login
            tvLinkGithub.text = dataUser.htmlUrl
        }

    }

    private fun CircleImageView.loadImageUser(urlImage: String?) {
        Glide.with(this.context).load(urlImage).into(this)
    }
}