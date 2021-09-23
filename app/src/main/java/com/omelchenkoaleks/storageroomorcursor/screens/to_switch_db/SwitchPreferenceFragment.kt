package com.omelchenkoaleks.storageroomorcursor.screens.to_switch_db

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.omelchenkoaleks.storageroomorcursor.R

class SwitchPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.to_switch_db, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val switchRoom = findPreference(getString(R.string.to_room)) as SwitchPreference?
        switchRoom?.isChecked = true

        val switchCursor = findPreference(getString(R.string.to_cursor)) as SwitchPreference?

        switchRoom.let {
            it?.setOnPreferenceChangeListener { _, _ ->
                if (switchCursor?.isChecked == true) {
                    switchCursor.isChecked = false
                }
                true
            }
        }

        switchCursor.let {
            it?.setOnPreferenceChangeListener { _, _ ->
                if (switchRoom?.isChecked == true) {
                    switchRoom.isChecked = false
                }
                true
            }
        }

    }
}