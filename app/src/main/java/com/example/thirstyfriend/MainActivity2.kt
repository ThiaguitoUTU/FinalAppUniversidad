package com.example.thirstyfriend

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main2, container, false)

        val nombreEditText = view.findViewById<EditText>(R.id.nombreEditText)
        val continueButton = view.findViewById<Button>(R.id.continue_button)

        continueButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase

            // Guardar el nombre en la base de datos
            val values = ContentValues().apply {
                put("nombre", nombre)
            }

            val newRowId = db.insert("usuario", null, values)

            if (newRowId != -1L) {
                Toast.makeText(requireContext(), "Nombre agregado exitosamente", Toast.LENGTH_SHORT).show()

                // Pasar el ID del usuario al siguiente fragmento
                val fragment = MainActivity3()
                val bundle = Bundle()
                bundle.putLong("USER_ID", newRowId)
                fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Hubo un error al agregar el nombre", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }

        return view
    }
}
