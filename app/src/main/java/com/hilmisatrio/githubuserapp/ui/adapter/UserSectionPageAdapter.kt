package com.hilmisatrio.githubuserapp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hilmisatrio.githubuserapp.ui.detail.FollowFragment
import com.hilmisatrio.githubuserapp.utils.ConstantValue

class UserSectionPageAdapter(fragment: Fragment, usernameGithub: String) :
    FragmentStateAdapter(fragment) {
    var username: String = ""

    init {
        username = usernameGithub
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        fragment.arguments = Bundle().apply {
            putInt(ConstantValue.POSITION_FRAGMENT, position + 1)
            putString(ConstantValue.USERNAME, username)
        }

        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}