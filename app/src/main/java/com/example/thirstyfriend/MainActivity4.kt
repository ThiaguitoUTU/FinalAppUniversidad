package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main4, container, false)

        // Configurar NumberPicker
        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPickerpeso)
        numberPicker.apply {
            minValue = 1
            maxValue = 209
            value = 65
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setFormatter { value -> "$value kg" }
        }

        // Configurar la animación
        val goticaImage = view.findViewById<ImageView>(R.id.goticaImage)
        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.animation)
            .into(goticaImage)

        // Configurar la barra de progreso
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 75

        val userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            Toast.makeText(this, "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // Configurar el botón para continuar
        val botonContinuar = findViewByID<Button>(R.id.botonContinuar)
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

        // Configurar el botón
        view.findViewById<Button>(R.id.botonContinuar).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainActivity5())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}