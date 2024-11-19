package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity5 : Fragment() {

    private var userId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main5, container, false)

        // Obtener el userId desde los argumentos del fragmento
        userId = arguments?.getLong("USER_ID", -1L) ?: -1L
        if (userId == -1L) {
            Toast.makeText(requireContext(), "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return view
        }

        // Configurar NumberPicker para la edad
        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker1)
        numberPicker.apply {
            minValue = 1
            maxValue = 80
            value = 25
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
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
        progressBar.progress = 100

        // Configurar el botón para guardar la edad en la base de datos
        view.findViewById<Button>(R.id.botonContinuar).setOnClickListener {
            val edadSeleccionada = numberPicker.value

            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase

            // Preparar los valores para actualizar la base de datos
            val values = ContentValues().apply {
                put("edad", edadSeleccionada)
            }

            // Actualizar la edad del usuario en la base de datos
            val selection = "id = ?"
            val selectionArgs = arrayOf(userId.toString())

            val count = db.update("usuario", values, selection, selectionArgs)

            if (count > 0) {
                // Guardar el estado de onboarding en SharedPreferences
                val sharedPrefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                sharedPrefs.edit().apply {
                    putBoolean("onboarding_completed", true)
                    apply()
                }

                // Notificar éxito al usuario
                Toast.makeText(requireContext(), "Edad actualizada correctamente", Toast.LENGTH_SHORT).show()

                // Pasar a la siguiente actividad con banderas para limpiar el stack
                val intent = Intent(requireContext(), Principal::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

                // Finalizar la actividad asociada al fragmento
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Hubo un error al actualizar la edad", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }
        return view
    }
}