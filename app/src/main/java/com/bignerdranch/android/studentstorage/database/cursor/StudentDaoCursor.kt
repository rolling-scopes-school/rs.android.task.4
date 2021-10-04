package com.bignerdranch.android.studentstorage.database.cursor

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.bignerdranch.android.studentstorage.database.room.StudentDao
import com.bignerdranch.android.studentstorage.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val DATABASE_NAME = "Student-database"
private const val TABLE_NAME = "Student"
private const val DATABASE_VERSION = 1
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "id	INTEGER NOT NULL," +
            "name	TEXT NOT NULL," +
            "age	INTEGER NOT NULL," +
            "rating	REAL NOT NULL," +
            "PRIMARY KEY(id AUTOINCREMENT)" +
            ");"
private const val TAG = "A31242564653453543"

class StudentDatabaseCursor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), StudentDao {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE_SQL)
        }
        catch (exception: SQLException) {
            print(exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    override fun getStudent(id: Int): LiveData<Student?> {
        Log.d(TAG, "Cursor get student")
        val studentLiveData = MutableLiveData<Student>()
        val db = writableDatabase

        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE id = $id"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val age = cursor.getInt(cursor.getColumnIndex("age"))
                    val rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                    val student = Student(id, name, age, rating)

                    studentLiveData.value = student
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return studentLiveData
    }

    override fun getStudents(sortMode: String): LiveData<List<Student>> =
        liveData { emit(getStudentsList(sortMode)) }

    private suspend fun getStudentsList(orderList: String):List<Student>{
        Log.d(TAG, "Cursor get students list")
        return withContext(Dispatchers.IO) {
            val listOfStudents = mutableListOf<Student>()
            val db = writableDatabase
            val query = if (orderList == "rating") "$orderList DESC" else orderList
            val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $query"
            val cursor = db.rawQuery(selectQuery,null)
            cursor?.let{
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getInt(cursor.getColumnIndex("id"))
                        val name = cursor.getString(cursor.getColumnIndex("name"))
                        val age = cursor.getInt(cursor.getColumnIndex("age"))
                        val rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                        listOfStudents.add(Student(id, name, age, rating))
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            listOfStudents
        }
    }

    override fun addStudent(student: Student) {
        Log.d(TAG, "Cursor add")
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("age", student.age)
            put("rating", student.rating)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun updateStudent(student: Student) {
        Log.d(TAG, "Cursor update")
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", student.id)
            put("name", student.name)
            put("age", student.age)
            put("rating", student.rating)
        }

        db.update(TABLE_NAME, values, "id" + " = ?", arrayOf(student.id.toString()))
        db.close()
    }

    override fun deleteStudent(student: Student) {
        Log.d(TAG, "Cursor delete")
        val db = writableDatabase
        db.delete(TABLE_NAME, "id" + " = ?", arrayOf(student.id.toString()))
        db.close()
    }
}