package com.omelchenkoaleks.storageroomorcursor.screens.sort

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.omelchenkoaleks.storageroomorcursor.R
import com.omelchenkoaleks.storageroomorcursor.databinding.FragmentSortBinding

class SortFragment : Fragment() {

    private var _binding: FragmentSortBinding? = null
    private val binding get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}