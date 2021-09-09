package com.bignerdranch.android.studentstorage.viewmodel

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.studentstorage.database.room.StudentRepository
import com.bignerdranch.android.studentstorage.model.Student

class StudentListViewModel : ViewModel() {
    private val studentRepository = StudentRepository.get()
    var studentListLiveData = studentRepository.getStudents("name")

    fun sortStudents(modeOfSort: String) {
        studentListLiveData = studentRepository.getStudents(modeOfSort)
    }

    fun addStudent(student: Student) {
        studentRepository.addStudent(student)
    }

    fun deleteStudent(student: Student) {
        studentRepository.deleteStudent(student)
    }
}