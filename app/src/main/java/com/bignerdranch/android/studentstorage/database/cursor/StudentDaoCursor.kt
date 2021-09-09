package com.bignerdranch.android.studentstorage.database.cursor

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

    override fun addStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", student.name)
        values.put("age", student.age)
        values.put("rating", student.rating)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun getStudent(id: Int): LiveData<Student?> {
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

    override fun getStudents(sortMode: String): LiveData<List<Student>> {
        return liveData { emit(getStudentsList(sortMode)) }
    }

    private suspend fun getStudentsList(orderList: String):List<Student>{
        return withContext(Dispatchers.IO) {
            val listOfStudents = mutableListOf<Student>()
            val db = writableDatabase
            val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $orderList"
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

    override fun updateStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues()

        values.put("id", student.id)
        values.put("name", student.name)
        values.put("age", student.age)
        values.put("rating", student.rating)

        db.update(TABLE_NAME, values, "id" + "=?", arrayOf(student.id.toString()))
        db.close()
    }

    override fun deleteStudent(student: Student) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "id" + "=?", arrayOf(student.id.toString()))
        db.close()
    }
}