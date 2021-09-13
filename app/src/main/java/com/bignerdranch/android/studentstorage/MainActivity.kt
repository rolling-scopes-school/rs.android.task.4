package com.bignerdranch.android.studentstorage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.studentstorage.fragments.StudentFragment
import com.bignerdranch.android.studentstorage.fragments.StudentListFragment
import com.bignerdranch.android.studentstorage.fragments.StudentSortingFragment
import com.bignerdranch.android.studentstorage.fragments.StudentPreferenceFragment
import com.bignerdranch.android.studentstorage.model.Student

class MainActivity : AppCompatActivity(), Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm: FragmentManager = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = StudentListFragment.newInstance(sortingMode = "name")
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    override fun onMainScreen(student: Student?, sortingMode: String) {
        this.setTitle(R.string.app_name)
        startFragment(StudentListFragment.newInstance(student, sortingMode))
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