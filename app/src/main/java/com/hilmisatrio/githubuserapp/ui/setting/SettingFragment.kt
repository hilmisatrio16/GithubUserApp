package com.hilmisatrio.githubuserapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hilmisatrio.githubuserapp.data.local.dsprefs.SettingAppPreferences
import com.hilmisatrio.githubuserapp.data.local.dsprefs.dataStore
import com.hilmisatrio.githubuserapp.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingPref =
            SettingAppPreferences.getInstance(requireContext().applicationContext.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(settingPref)
        )[SettingViewModel::class.java]

        setThemeApplication(settingViewModel)

        binding.swicthTheme.setOnCheckedChangeListener { _, checked ->
            settingViewModel.saveThemeMode(checked)
        }

        binding.btnBack.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun setThemeApplication(settingViewModel: SettingViewModel) {
        settingViewModel.getThemeMode().observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swicthTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swicthTheme.isChecked = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}