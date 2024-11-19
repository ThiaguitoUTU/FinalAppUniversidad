package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class UsuarioFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_fragment)

        val userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            Toast.makeText(this, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val usuario = dbHelper.getUsuario(userId)
        if (usuario != null) {
            findViewById<TextView>(R.id.tvNombre).text = usuario.nombre
            findViewById<TextView>(R.id.tvGenero).text = usuario.genero ?: "No especificado"
            findViewById<TextView>(R.id.tvPeso).text = usuario.peso?.toString() ?: "No especificado"
            findViewById<TextView>(R.id.tvEdad).text = usuario.edad?.toString() ?: "No especificado"
            mostrarHistorialAgua(userId)
            mostrarHistorialMetas(userId)
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }

        val startButton = findViewById<Button>(R.id.btnVolver)
        startButton.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }

        val characterImageView = findViewById<ImageView>(R.id.characterImageView)
        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(characterImageView)
    }

    private fun mostrarHistorialAgua(usuarioId: Long) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT cantidad, fecha FROM registro_agua WHERE usuario_id = ? ORDER BY fecha DESC",
            arrayOf(usuarioId.toString())
        )

        val historial = StringBuilder()
        while (cursor.moveToNext()) {
            val cantidad = cursor.getInt(0)
            val fecha = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(cursor.getLong(1))
            historial.append("$fecha: ${cantidad}ml\n")
        }
        cursor.close()
        db.close()

        findViewById<TextView>(R.id.tvHistorial).text = historial.toString()
    }

    private fun mostrarHistorialMetas(usuarioId: Long) {
        val dbHelper = DatabaseHelper(this)
        val historialMetas = dbHelper.getHistorialMetas(usuarioId)

        val historialMetasTexto = historialMetas.joinToString("\n")
        findViewById<TextView>(R.id.tvHistorial).text = historialMetasTexto
    }
}
