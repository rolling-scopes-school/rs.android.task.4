package com.bignerdranch.android.studentstorage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.studentstorage.database.room.StudentRepository
import com.bignerdranch.android.studentstorage.model.Student

class StudentDetailViewModel : ViewModel() {
    private val studentRepository = StudentRepository.get()
    private val studentIdLiveData = MutableLiveData<Int>()

    val studentLiveData: LiveData<Student?> =
        Transformations.switchMap(studentIdLiveData) { studentID ->
            studentRepository.getStudent(studentID)
        }

    fun loadStudent(studentID: Int) {
        studentIdLiveData.value = studentID
    }

    fun addStudent(student: Student) {
        studentRepository.addStudent(student)
    }

    fun saveStudent(student: Student) {
        studentRepository.updateStudent(student)
    }

    fun deleteStudent(student: Student){
        studentRepository.deleteStudent(student)
    }
}