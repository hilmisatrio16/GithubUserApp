package com.hilmisatrio.githubuserapp.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hilmisatrio.githubuserapp.R
import com.hilmisatrio.githubuserapp.data.local.dsprefs.SettingAppPreferences
import com.hilmisatrio.githubuserapp.data.local.dsprefs.dataStore
import com.hilmisatrio.githubuserapp.utils.ConstantValue.TIME_DURATION
import com.hilmisatrio.githubuserapp.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {
            setThemeApplication()
        }

    }

    private fun setThemeApplication() {
        val settingPref =
            SettingAppPreferences.getInstance(requireContext().applicationContext.dataStore)
        val splashScreenViewModel = ViewModelProvider(
            this,
            SplashScreenViewModelFactory(settingPref)
        )[SplashScreenViewModel::class.java]

        splashScreenViewModel.getThemeMode().observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }

        }, TIME_DURATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}