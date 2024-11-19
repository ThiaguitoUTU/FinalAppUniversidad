package com.example.thirstyfriend

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val characterImageView = findViewById<ImageView>(R.id.characterImageView)

        // Cargar el GIF usando Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(characterImageView)

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val nombreEditText = findViewById<EditText>(R.id.nombreEditText)

        val continueButton = findViewById<Button>(R.id.continue_button)
        continueButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()

            // Validación del campo de nombre
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar el nombre en la base de datos
            val values = ContentValues().apply {
                put("nombre", nombre)
            }

            val newRowId = db.insert("usuario", null, values)

            if (newRowId != -1L) {
                // Mostrar mensaje de éxito
                Toast.makeText(this, "Nombre agregado exitosamente", Toast.LENGTH_SHORT).show()

                // Continuar a la siguiente actividad y pasar el ID del usuario
                val intent = Intent(this, MainActivity3::class.java)
                intent.putExtra("USER_ID", newRowId) // Pasar el ID del nuevo usuario
                startActivity(intent)
            } else {
                // Mostrar error si hubo un problema al insertar
                Toast.makeText(this, "Hubo un error al agregar el nombre", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 25
    }
}
