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

        // Obtener el ID del usuario pasado en el Intent
        val userId = intent.getLongExtra("USER_ID", -1L)

        if (userId == -1L) {
            Toast.makeText(this, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Obtener los datos del usuario de la base de datos
        val dbHelper = DatabaseHelper(this)
        val usuario = dbHelper.getUsuario(userId)

        // Verificar si el usuario existe y mostrar los datos
        if (usuario != null) {
            findViewById<TextView>(R.id.tvNombre).text = usuario.nombre
            findViewById<TextView>(R.id.tvGenero).text = usuario.genero ?: "No especificado"
            findViewById<TextView>(R.id.tvPeso).text = usuario.peso?.toString() ?: "No especificado"
            findViewById<TextView>(R.id.tvEdad).text = usuario.edad?.toString() ?: "No especificado"
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
}