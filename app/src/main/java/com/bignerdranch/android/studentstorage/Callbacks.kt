package com.bignerdranch.android.studentstorage

import com.bignerdranch.android.studentstorage.model.Student

interface Callbacks {
    fun onCreateNewStudent()
    fun onStudentSelected(studentID: Int)
    fun onSortSelected()
    fun onMainScreen(student: Student?, sortingMode: String)
    fun onSettingsScreen()
}