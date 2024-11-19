package com.example.thirstyfriend

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity3 : Fragment() {

    private var userId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main3, container, false)

        // Obtener el userId desde los argumentos del fragmento
        userId = arguments?.getLong("USER_ID", -1L) ?: -1L
        if (userId == -1L) {
            Toast.makeText(requireContext(), "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return view
        }

        val radioButtonHombre = view.findViewById<RadioButton>(R.id.radioButtonHombre)
        val radioButtonMujer = view.findViewById<RadioButton>(R.id.radioButtonMujer)
        val continueButton = view.findViewById<Button>(R.id.continue_button)

        continueButton.setOnClickListener {
            val genero = when {
                radioButtonHombre.isChecked -> "Hombre"
                radioButtonMujer.isChecked -> "Mujer"
                else -> null
            }

            if (genero == null) {
                Toast.makeText(requireContext(), "Por favor, seleccione un género.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("genero", genero)
            }

            val selection = "id = ?"
            val selectionArgs = arrayOf(userId.toString())
            val count = db.update("usuario", values, selection, selectionArgs)

            if (count > 0) {
                Toast.makeText(requireContext(), "Género actualizado correctamente", Toast.LENGTH_SHORT).show()

                // Pasar al siguiente fragmento
                val fragment = MainActivity4()
                val bundle = Bundle()
                bundle.putLong("USER_ID", userId)
                fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Hubo un error al actualizar el género", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }

        return view
    }
}
