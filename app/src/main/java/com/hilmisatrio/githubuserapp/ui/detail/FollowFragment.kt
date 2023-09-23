package com.hilmisatrio.githubuserapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseFollow
import com.hilmisatrio.githubuserapp.databinding.FragmentFollowBinding
import com.hilmisatrio.githubuserapp.ui.adapter.ListFollowAdapter
import com.hilmisatrio.githubuserapp.utils.ConstantValue

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(requireContext())
    }
    private var position: Int = 0
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ConstantValue.POSITION_FRAGMENT)
            username = it.getString(ConstantValue.USERNAME)
        }

        if (position != 0 && username != null) {
            if (position == 1) {
                observerDataFollowers(username!!)
            } else {
                observerDataFollowing(username!!)
            }
        }
    }

    private fun observerDataFollowers(username: String) {
        detailViewModel.getListFollowers(username).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> {
                        showShimmerList(false)
                        if (it.data.isNotEmpty()) {
                            binding.rvFollow.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.GONE
                            setRecycleView(it.data)
                        } else {
                            binding.rvFollow.visibility = View.GONE
                            binding.tvEmpty.visibility = View.VISIBLE
                            showEmpty()
                        }
                    }
                    is Result.Loading -> {
                        showShimmerList(true)
                    }
                    is Result.Error -> {
                        showShimmerList(true)
                        showSnackBar(it.error)
                    }
                }
            }
        }
    }

    private fun showEmpty() {
        if (position == 1) {
            binding.tvEmpty.text = resources.getString(R.string.empty_followers)
        } else {
            binding.tvEmpty.text = resources.getString(R.string.empty_following)
        }
    }

    private fun observerDataFollowing(username: String) {
        detailViewModel.getListFollowing(username).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> {
                        showShimmerList(false)
                        if (it.data.isNotEmpty()) {
                            binding.rvFollow.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.GONE
                            setRecycleView(it.data)
                        } else {
                            binding.rvFollow.visibility = View.GONE
                            binding.tvEmpty.visibility = View.VISIBLE
                            showEmpty()
                        }
                    }
                    is Result.Loading -> {
                        showShimmerList(true)
                    }
                    is Result.Error -> {
                        showShimmerList(true)
                        showSnackBar(it.error)
                    }
                }
            }
        }
    }

    private fun showShimmerList(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerListFollow.startShimmerAnimation()
            binding.rvFollow.visibility = View.GONE
            binding.shimmerListFollow.visibility = View.VISIBLE
        } else {
            binding.shimmerListFollow.stopShimmerAnimation()
            binding.rvFollow.visibility = View.VISIBLE
            binding.shimmerListFollow.visibility = View.GONE
        }
    }

    private fun setRecycleView(listFollow: ResponseFollow) {
        val listFollowAdapter = ListFollowAdapter()
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(context)
            listFollowAdapter.submitData(listFollow)
            adapter = listFollowAdapter
            setHasFixedSize(true)
        }
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            requireActivity().window.decorView.rootView, message, Snackbar.LENGTH_SHORT
        )

        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext().applicationContext,
                R.color.red_light
            )
        )
        snackbar.setTextColor(
            ContextCompat.getColor(
                requireContext().applicationContext,
                R.color.white
            )
        )
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}