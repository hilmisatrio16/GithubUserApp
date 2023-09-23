package com.hilmisatrio.githubuserapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.databinding.FragmentFavoriteBinding
import com.hilmisatrio.githubuserapp.ui.adapter.ListUserFavoriteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showListFavorite()

        binding.btnBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.btnSetting.setOnClickListener {
            it.findNavController().navigate(R.id.action_favoriteFragment_to_settingFragment)
        }
    }

    private fun showListFavorite() {
        showShimmerList(true)
        favoriteViewModel.getUsersFavorite().observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    showImageEmpty(false)
                } else {
                    showImageEmpty(true)
                }
                binding.shimmerListFavorite.visibility = View.GONE
                setRecycleView(it)
            } else {
                binding.shimmerListFavorite.visibility = View.GONE
                showImageEmpty(true)
            }
            binding.shimmerListFavorite.stopShimmerAnimation()
        }
    }

    private fun showImageEmpty(isVisible: Boolean) {
        if (isVisible) {
            binding.rvFavorite.visibility = View.GONE
            binding.animationEmpty.visibility = View.VISIBLE
        } else {
            binding.rvFavorite.visibility = View.VISIBLE
            binding.animationEmpty.visibility = View.GONE
        }
    }

    private fun showShimmerList(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerListFavorite.startShimmerAnimation()
            binding.rvFavorite.visibility = View.GONE
            binding.shimmerListFavorite.visibility = View.VISIBLE
        } else {
            binding.shimmerListFavorite.stopShimmerAnimation()
            binding.rvFavorite.visibility = View.VISIBLE
            binding.shimmerListFavorite.visibility = View.GONE
        }
    }

    private fun setRecycleView(listFavorite: List<UserFavorite>) {
        val listUserFavoriteAdapter = ListUserFavoriteAdapter()
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            listUserFavoriteAdapter.submitData(listFavorite as ArrayList<UserFavorite>)
            adapter = listUserFavoriteAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}