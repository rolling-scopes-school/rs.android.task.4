package com.example.workingwithstorage.fragments.filter

import android.os.Bundle
import android.view.*
import androidx.preference.PreferenceFragmentCompat
import com.example.workingwithstorage.R
import com.example.workingwithstorage.databinding.FragmentUpdateBinding


class FilterFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

    }

}