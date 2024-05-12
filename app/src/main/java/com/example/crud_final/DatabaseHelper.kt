package com.example.crud_final

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "infoapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allinfo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertInfo(info: Info){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, info.title)
            put(COLUMN_CONTENT, info.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getInfo():List<Info>{
        val InfoList = mutableListOf<Info>()
        val db = readableDatabase
        val retrievequery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(retrievequery, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val info = Info(id, title, content)
            InfoList.add(info)
        }
        cursor.close()
        db.close()
        return InfoList
    }

    fun update(info: Info){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, info.title)
            put(COLUMN_CONTENT, info.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(info.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getInfoById(infoId: Int): Info{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $infoId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Info(id, title, content)
    }

    fun deleteInfo(infoId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(infoId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}