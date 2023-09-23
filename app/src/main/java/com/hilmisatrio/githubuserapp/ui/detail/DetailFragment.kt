package com.hilmisatrio.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hilmisatrio.githubuserapp.data.Result
import com.google.android.material.tabs.TabLayoutMediator
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseDetailUser
import com.hilmisatrio.githubuserapp.databinding.FragmentDetailBinding
import com.hilmisatrio.githubuserapp.ui.adapter.UserSectionPageAdapter
import com.hilmisatrio.githubuserapp.utils.ConstantValue

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(requireContext())
    }
    private var linkAccountGithub: String? = null

    private var alreadyFavorite: Boolean? = null

    private var dataDetailUser: ResponseDetailUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getUsername = arguments?.getString(ConstantValue.USERNAME)



        if (getUsername != null) {
            setTabLayout(getUsername)
            observerDetailUser(getUsername)
            checkFavorite(getUsername)
        }

        binding.btnOpenGithub.setOnClickListener {
            if (linkAccountGithub != null) {
                val intentViewGithub = Intent(Intent.ACTION_VIEW, Uri.parse(linkAccountGithub))
                startActivity(intentViewGithub)
            }
        }

        binding.btnBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.btnSetting.setOnClickListener {
            it.findNavController().navigate(R.id.action_detailFragment_to_settingFragment)
        }

        binding.btnFavorite.setOnClickListener {
            setFavorite()
        }

    }

    private fun checkFavorite(username: String) {
        detailViewModel.checkFavoriteUser(username).observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnFavorite.apply {
                    imageTintList = ColorStateList.valueOf(resources.getColor(R.color.red_light))
                    alreadyFavorite = true
                }
            } else {
                binding.btnFavorite.apply {
                    imageTintList = ColorStateList.valueOf(resources.getColor(R.color.white_300))
                    alreadyFavorite = false
                }
            }
        }
    }

    private fun setFavorite() {
        if (alreadyFavorite != null && dataDetailUser != null) {
            val dataUserFavorite = UserFavorite(
                dataDetailUser!!.login,
                dataDetailUser!!.avatarUrl,
                dataDetailUser!!.htmlUrl
            )
            if (alreadyFavorite as Boolean) {
                detailViewModel.deleteFavorite(dataUserFavorite)
            } else {
                detailViewModel.addFavorite(dataUserFavorite)
            }
            checkFavorite(dataDetailUser!!.login)
        } else {
            showSnackBar("Sorry, can't add favorite!")
        }
    }

    private fun observerDetailUser(username: String) {
        detailViewModel.getDetail(username).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> {
                        showShimmerList(false)
                        showDetailUser(it.data)
                        dataDetailUser = it.data
                    }
                    is Result.Loading -> {
                        showShimmerList(true)
                    }
                    is Result.Error -> {
                        showSnackBar(it.error)
                        showShimmerList(true)
                    }
                }
            }
        }
    }

    private fun showShimmerList(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerDetailUser.startShimmerAnimation()
            binding.layoutProfileUser.visibility = View.GONE
            binding.shimmerDetailUser.visibility = View.VISIBLE
        } else {
            binding.shimmerDetailUser.stopShimmerAnimation()
            binding.layoutProfileUser.visibility = View.VISIBLE
            binding.shimmerDetailUser.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailUser(dataUser: ResponseDetailUser) {
        binding.apply {
            Glide.with(requireActivity())
                .load(dataUser.avatarUrl)
                .into(imageUser)
            tvFullName.text = dataUser.name
            tvUsername.text = dataUser.login
            tvLocationUser.text = dataUser.location
            tvCompany.text = dataUser.company
            tvFollowers.text = "${dataUser.followers} Followers"
            tvFollowing.text = "${dataUser.following} Following"
            tvRepository.text = "${dataUser.publicRepos} Repository"
        }
        linkAccountGithub = dataUser.htmlUrl
    }

    private fun setTabLayout(username: String) {
        val profileUserPagerAdapter = UserSectionPageAdapter(this, username)

        binding.viewPagerUser.adapter = profileUserPagerAdapter
        TabLayoutMediator(binding.tabMenuUser, binding.viewPagerUser) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_txt,
            R.string.following_txt
        )
    }

}