package com.example.thirstyfriend

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "mi_base_de_datos.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
        CREATE TABLE usuario (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT,
            genero TEXT,
            peso INT,
            edad INTEGER
        )
    """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        onCreate(db)
    }
}
