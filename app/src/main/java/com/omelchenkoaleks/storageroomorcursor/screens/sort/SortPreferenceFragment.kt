package com.omelchenkoaleks.storageroomorcursor.screens.sort

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.omelchenkoaleks.storageroomorcursor.R

class SortPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.sort, rootKey)
//        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        addPreferencesFromResource(R.xml.sort)

        val switchName = findPreference(getString(R.string.key_name)) as SwitchPreference?
        val switchAge = findPreference(getString(R.string.key_age)) as SwitchPreference?
        val switchBreed = findPreference(getString(R.string.key_breed)) as SwitchPreference?

        switchName.let {
            it?.setOnPreferenceChangeListener { _, _ ->
                if (switchAge?.isChecked == true || switchBreed?.isChecked == true) {
                    if (switchAge?.isChecked == true) switchAge.isChecked = false
                    if (switchBreed?.isChecked == true) switchBreed.isChecked = false
                }
                true
            }
        }

        switchAge.let {
            it?.setOnPreferenceChangeListener { _, _ ->
                if (switchName?.isChecked == true || switchBreed?.isChecked == true) {
                    if (switchName?.isChecked == true) switchName.isChecked = false
                    if (switchBreed?.isChecked == true) switchBreed.isChecked = false
                }
                true
            }
        }

        switchBreed.let {
            it?.setOnPreferenceChangeListener { _, _ ->
                if (switchName?.isChecked == true || switchAge?.isChecked == true) {
                    if (switchName?.isChecked == true) switchName.isChecked = false
                    if (switchAge?.isChecked == true) switchAge.isChecked = false
                }
                true
            }
        }
    }

}