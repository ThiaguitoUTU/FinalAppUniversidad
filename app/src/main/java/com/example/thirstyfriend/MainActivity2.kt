package com.example.thirstyfriend

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main2, container, false)

        // Configurar la animación
        val characterImageView = view.findViewById<ImageView>(R.id.characterImageView)
        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.animation)
            .into(characterImageView)

        // Configurar la barra de progreso
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 25

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


        // Configurar el botón
        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main2, MainActivity3())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}