package com.hilmisatrio.githubuserapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.remote.response.ItemUser
import com.hilmisatrio.githubuserapp.databinding.FragmentHomeBinding
import com.hilmisatrio.githubuserapp.ui.adapter.ListUsersAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchQueryUser()
        if (!homeViewModel.dataSearch.value.isNullOrEmpty()) {
            observerDataUser(homeViewModel.dataSearch.value.toString())
        } else {
            observerDataUser()
        }


        binding.btnSetting.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        binding.btnFavorite.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun observerDataUser(username: String = "David") {
        homeViewModel.getUsers(username).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> {
                        showShimmerList(false)
                        if (it.data.items.isNotEmpty()) {
                            binding.rvUser.visibility = View.VISIBLE
                            binding.tvNotFound.visibility = View.GONE
                            setRecycleView(it.data.items as ArrayList<ItemUser>)
                        } else {
                            binding.rvUser.visibility = View.GONE
                            binding.tvNotFound.visibility = View.VISIBLE
                            showSnackBar("User not found")
                        }

                    }
                    is Result.Loading -> {
                        showShimmerList(true)
                    }
                    is Result.Error -> {
                        showShimmerList(false)
                        showSnackBar(it.error)
                    }
                }
            }
        }
    }

    private fun showShimmerList(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerListUser.startShimmerAnimation()
            binding.rvUser.visibility = View.GONE
            binding.shimmerListUser.visibility = View.VISIBLE
        } else {
            binding.shimmerListUser.stopShimmerAnimation()
            binding.rvUser.visibility = View.VISIBLE
            binding.shimmerListUser.visibility = View.GONE
        }
    }

    private fun setRecycleView(listUser: ArrayList<ItemUser>) {
        val listUserAdapter = ListUsersAdapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            listUserAdapter.submitData(listUser)
            adapter = listUserAdapter
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

    private fun searchQueryUser() {
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    observerDataUser(query)
                    homeViewModel._dataSearch.postValue(query)
                }

                if (query.isNullOrEmpty()) {
                    homeViewModel._dataSearch.postValue(null)
                }

                binding.searchUser.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    homeViewModel._dataSearch.postValue(null)
                }
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}