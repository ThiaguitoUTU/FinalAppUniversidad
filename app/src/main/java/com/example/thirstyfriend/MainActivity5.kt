package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity5 : AppCompatActivity() {
    private lateinit var numberPicker: NumberPicker
    private lateinit var botonContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        numberPicker = findViewById(R.id.numberPicker1)
        botonContinuar = findViewById(R.id.botonContinuar)

        // Configurar el NumberPicker
        numberPicker.apply {
            minValue = 1
            maxValue = 80  // Rango de edad
            value = 25  // Edad inicial

            // Quitar el teclado al hacer clic
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

        // Listener para cuando el valor cambia
        numberPicker.setOnValueChangedListener { _, _, newVal ->
            Log.d("AgePicker", "Edad seleccionada: $newVal años")
        }

        // Acción del botón Continuar
        botonContinuar.setOnClickListener {
            val selectedAge = numberPicker.value
            Log.d("AgePicker", "Edad confirmada: $selectedAge años")
            // Aquí puedes añadir la lógica para continuar con la edad seleccionada
        }

        val goticaImag = findViewById<ImageView>(R.id.goticaImage)

        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(goticaImag)


        val botonContinuar = findViewById<Button>(R.id.botonContinuar)
        botonContinuar.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
}
