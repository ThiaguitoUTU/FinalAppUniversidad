package com.example.thirstyfriend

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity4 : Fragment() {

    private var userId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main4, container, false)

        userId = arguments?.getLong("USER_ID", -1L) ?: -1L
        if (userId == -1L) {
            Toast.makeText(requireContext(), "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_SHORT).show()
            return view
        }

        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPickerpeso)
        numberPicker.apply {
            minValue = 1
            maxValue = 209
            value = 65
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

        val continueButton = view.findViewById<Button>(R.id.continue_button)
        continueButton.setOnClickListener {
            val peso = numberPicker.value

            val dbHelper = DatabaseHelper(requireContext())
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("peso", peso)
            }

            val selection = "id = ?"
            val selectionArgs = arrayOf(userId.toString())
            val count = db.update("usuario", values, selection, selectionArgs)

            if (count > 0) {
                Toast.makeText(requireContext(), "Peso actualizado correctamente", Toast.LENGTH_SHORT).show()

                // Pasar al siguiente fragmento
                val fragment = MainActivity5() // Asegúrate de tener MainActivity5 en tu código
                val bundle = Bundle()
                bundle.putLong("USER_ID", userId)
                fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Hubo un error al actualizar el peso", Toast.LENGTH_SHORT).show()
            }

            db.close()
        }

        return view
    }
}
