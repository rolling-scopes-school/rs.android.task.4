package com.bignerdranch.android.studentstorage

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.bignerdranch.android.studentstorage.fragments.StudentFragment
import com.bignerdranch.android.studentstorage.fragments.StudentListFragment
import com.bignerdranch.android.studentstorage.fragments.StudentPreferenceFragment
import com.bignerdranch.android.studentstorage.fragments.StudentSortingFragment

class MainActivity : AppCompatActivity(), Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm: FragmentManager = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null)
            onMainScreen("name")
    }

    override fun onMainScreen(sortingMode: String) {
        this.setTitle(R.string.app_name)
        startFragment(StudentListFragment.newInstance(sortingMode))

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val keyCondition = sharedPreferences
            .getBoolean("switchBackgroundMusicPlay", false)
        val modeCondition = audioManager.ringerMode == AudioManager.RINGER_MODE_NORMAL

        val intent = Intent(this, BackgroundSoundService::class.java)
        if (keyCondition && modeCondition)
            startService(intent)
        else stopService(intent)
    }

    override fun onCreateNewStudent() {
        this.setTitle(R.string.adding_label)
        startFragment(StudentFragment.newInstance())
    }

    override fun onStudentSelected(studentID: Int) {
        this.setTitle(R.string.editing_label)
        startFragment(StudentFragment.newInstance(studentID))
    }

    override fun onSortSelected() {
        this.setTitle(R.string.sorting_label)
        startFragment(StudentSortingFragment.newInstance())
    }

    override fun onSettingsScreen() {
        this.setTitle(R.string.setting_label)
        startFragment(StudentPreferenceFragment.newInstance())
    }

    private fun startFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}