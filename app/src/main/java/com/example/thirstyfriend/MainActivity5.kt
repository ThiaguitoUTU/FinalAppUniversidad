package com.example.thirstyfriend

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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity5 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main5, container, false)

        // Configurar NumberPicker
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

        // Configurar el botón
        view.findViewById<Button>(R.id.botonContinuar).setOnClickListener {
            // Iniciar la actividad Principal
            startActivity(Intent(requireContext(), Principal::class.java))
        }

        return view
    }
}