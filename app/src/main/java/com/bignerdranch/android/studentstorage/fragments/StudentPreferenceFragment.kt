package com.bignerdranch.android.studentstorage.fragments

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.preference.PreferenceFragmentCompat
import com.bignerdranch.android.studentstorage.Callbacks
import com.bignerdranch.android.studentstorage.R

class StudentPreferenceFragment : PreferenceFragmentCompat() {
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            callbacks?.onMainScreen("name")
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudentPreferenceFragment()
    }
}