package com.example.workingwithstorage.SQLite

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal const val TOPIC_COLUMN = "TOPIC_NAME"

private const val LOG_TAG = "RSSchoolSQLiteOpenHelper"
private const val DATABASE_NAME = "RS_ANDROID"
private const val TABLE_NAME = "film_table"
private const val DATABASE_VERSION = 1
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $TOPIC_COLUMN VARCHAR(50));"


class FilmSQLiteHelper(context: Context) : SQLiteOpenHelper (
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
     db.execSQL(CREATE_TABLE_SQL)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  //      db.execSQL(DELETE_TABLE)
        onCreate(db)
    }

//    fun getCursorWithTopics(): Cursor {
//        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
//    }
//
//    fun getListOfTopics(): List<String> {
//        val listOfTopics = mutableListOf<String>()
//        getCursorWithTopics().use { cursor ->
//            if (cursor.moveToFirst()) {
//                do {
//                    val topicName = cursor.getString(cursor.getColumnIndex(TOPIC_COLUMN))
//                    listOfTopics.add("From list: $topicName")
//                } while (cursor.moveToNext())
//            }
//        }
//        return listOfTopics
//    }
}