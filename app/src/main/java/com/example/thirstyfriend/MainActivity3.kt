package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity3 : Fragment() {

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

        // Recuperar el ID del usuario desde la actividad anterior (si se pasa entre actividades)
        val userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            Toast.makeText(this, "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // Obtener los radio buttons de género
        val generoRadioButtonHombre = findViewById<RadioButton>(R.id.radioButtonHombre)
        val generoRadioButtonMujer = findViewById<RadioButton>(R.id.radioButtonMujer)

        // Configurar el botón para continuar
        val continueButton = findViewById<Button>(R.id.botonContinuar)
        continueButton.setOnClickListener {
            // Validación: Verificar si se seleccionó un género
            if (!generoRadioButtonHombre.isChecked && !generoRadioButtonMujer.isChecked) {
                Toast.makeText(this, "Por favor, seleccione un género.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Determinar el género seleccionado
            val genero = if (generoRadioButtonHombre.isChecked) {
                "Hombre"
            } else {
                "Mujer"
            }

            // Preparar los valores a insertar en la base de datos
            val values = ContentValues().apply {
                put("genero", genero)
            }

            // Actualizar el género del usuario en la base de datos
            val selection = "id = ?"
            val selectionArgs = arrayOf(userId.toString())

            val count = db.update("usuario", values, selection, selectionArgs)

            if (count > 0) {
                // Mostrar mensaje de éxito
                Toast.makeText(this, "Género actualizado correctamente.", Toast.LENGTH_SHORT).show()

                // Continuar a la siguiente actividad
                val intent = Intent(this, MainActivity4::class.java)
                startActivity(intent)
            } else {
                // Si no se actualizó ninguna fila, mostrar un mensaje de error
                Toast.makeText(this, "Hubo un error al actualizar el género.", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }


        // Configurar el botón
        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main3, MainActivity4())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}


