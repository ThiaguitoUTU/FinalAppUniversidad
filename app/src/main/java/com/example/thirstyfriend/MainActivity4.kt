package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity4 : AppCompatActivity() {
    private lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        // Configuración del NumberPicker
        numberPicker = findViewById(R.id.numberPickerpeso)  // Usar el ID correcto aquí
        numberPicker.apply {
            minValue = 1
            maxValue = 209
            value = 65  // Valor inicial

            // Opcional: Quitar el teclado al hacer click
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            // Opcional: Formato para mostrar "kg" junto al número
            setFormatter { value -> "$value kg" }
        }

        val goticaImage = findViewById<ImageView>(R.id.goticaImage)

        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(goticaImage)

        // Recuperar el ID del usuario de la actividad anterior
        val userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            Toast.makeText(this, "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // Configurar el botón para continuar
        val botonContinuar = findViewById<Button>(R.id.botonContinuar)
        botonContinuar.setOnClickListener {
            // Obtener el peso seleccionado
            val pesoSeleccionado = numberPicker.value
            Log.d("WeightPicker", "Peso seleccionado para la base de datos: $pesoSeleccionado kg")

            // Validar que el peso esté dentro del rango permitido
            if (pesoSeleccionado < 1 || pesoSeleccionado > 209) {
                Toast.makeText(this, "Por favor, seleccione un peso válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Preparar los valores para la base de datos
            val values = ContentValues().apply {
                put("peso", pesoSeleccionado)
            }

            // Actualizar el peso del usuario en la base de datos
            val selection = "id = ?"
            val selectionArgs = arrayOf(userId.toString())

            val count = db.update("usuario", values, selection, selectionArgs)

            if (count > 0) {
                // Si se actualizó correctamente
                Toast.makeText(this, "Peso actualizado correctamente.", Toast.LENGTH_SHORT).show()

                // Finalizar la actividad y regresar
                finish()
            } else {
                // Mostrar error si no se actualizó
                Toast.makeText(this, "Hubo un error al actualizar el peso.", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }

        // Configuración del ProgressBar
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 75
    }
}
