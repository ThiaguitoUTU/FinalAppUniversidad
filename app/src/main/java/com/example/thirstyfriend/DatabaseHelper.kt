package com.example.thirstyfriend

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

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

    // Método para obtener un usuario a partir de su ID
    fun getUsuario(id: Long): Usuario? {
        val db = this.readableDatabase
        var usuario: Usuario? = null

        val cursor: Cursor? = db.query(
            "usuario",
            arrayOf("id", "nombre", "genero", "peso", "edad"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                genero = cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                peso = cursor.getIntOrNull(cursor.getColumnIndexOrThrow("peso")),
                edad = cursor.getIntOrNull(cursor.getColumnIndexOrThrow("edad"))
            )
            cursor.close()
        }

        db.close()
        return usuario
    }

    // Método para obtener un entero o null del cursor
    private fun Cursor.getIntOrNull(columnIndex: Int): Int? {
        return if (isNull(columnIndex)) null else getInt(columnIndex)
    }
}
