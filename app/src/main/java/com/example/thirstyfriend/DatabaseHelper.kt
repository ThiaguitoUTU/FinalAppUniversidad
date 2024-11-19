package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        val createTableUsuario = """
        CREATE TABLE usuario (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT,
            genero TEXT,
            peso INT,
            edad INTEGER
        )
        """
        db?.execSQL(createTableUsuario)

        val createTableRegistroAgua = """
        CREATE TABLE registro_agua (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER,
            cantidad INTEGER,
            fecha INTEGER,
            FOREIGN KEY(usuario_id) REFERENCES usuario(id)
        )
        """
        db?.execSQL(createTableRegistroAgua)

        val createTableHistorialMetas = """
        CREATE TABLE historial_metas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER,
            fecha INTEGER,
            FOREIGN KEY(usuario_id) REFERENCES usuario(id)
        )
        """
        db?.execSQL(createTableHistorialMetas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        db?.execSQL("DROP TABLE IF EXISTS registro_agua")
        db?.execSQL("DROP TABLE IF EXISTS historial_metas")
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

    // Método para registrar la cantidad de agua tomada
    fun registrarTomaAgua(usuarioId: Long, cantidad: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", usuarioId)
            put("cantidad", cantidad)
            put("fecha", System.currentTimeMillis())
        }
        db.insert("registro_agua", null, values)
        db.close()
    }

    // Método para registrar que se alcanzó una meta
    fun registrarMetaAlcanzada(usuarioId: Long, fecha: Long) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", usuarioId)
            put("fecha", fecha)
        }
        db.insert("historial_metas", null, values)
        db.close()
    }

    // Método para obtener el historial de metas alcanzadas
    fun getHistorialMetas(usuarioId: Long): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT fecha FROM historial_metas WHERE usuario_id = ? ORDER BY fecha DESC",
            arrayOf(usuarioId.toString())
        )

        val metas = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val fecha = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(cursor.getLong(0))
            metas.add("Meta alcanzada el $fecha")
        }
        cursor.close()
        db.close()
        return metas
    }

    // Método para obtener el total de agua consumida por el usuario
    fun getTotalAguaConsumida(usuarioId: Long): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM(cantidad) FROM registro_agua WHERE usuario_id = ?",
            arrayOf(usuarioId.toString())
        )
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return total
    }

    // Método para reiniciar los registros de agua de un usuario
    fun reiniciarRegistros(usuarioId: Long) {
        val db = this.writableDatabase
        db.delete("registro_agua", "usuario_id = ?", arrayOf(usuarioId.toString()))
        db.close()
    }

    // Método para obtener un entero o null del cursor
    private fun Cursor.getIntOrNull(columnIndex: Int): Int? {
        return if (isNull(columnIndex)) null else getInt(columnIndex)
    }
}
