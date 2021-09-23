package com.omelchenkoaleks.storageroomorcursor.screens.to_switch_db

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omelchenkoaleks.storageroomorcursor.databinding.FragmentSwitchBinding

class SwitchFragment : Fragment() {

    private var _binding: FragmentSwitchBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}