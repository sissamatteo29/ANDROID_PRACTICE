package com.msissa.android_practice.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.msissa.android_practice.R
import com.msissa.android_practice.databinding.FragmentNewQuotationBinding
import com.msissa.android_practice.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding : FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

