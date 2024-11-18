package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class MainActivity4 : AppCompatActivity() {
    private lateinit var numberPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        numberPicker = findViewById(R.id.numberPicker)

        // Configurar el NumberPicker
        numberPicker.apply {
            minValue = 1
            maxValue = 209
            value = 65  // Valor inicial

            // Opcional: Quitar el teclado al hacer click
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            // Opcional: Formato para mostrar "kg" junto al número
            setFormatter { value -> "$value kg" }
        }

        // Opcional: Listener para cuando el valor cambia
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            // Aquí puedes manejar el cambio de valor
            Log.d("WeightPicker", "Peso seleccionado $newVal kg")
        }

        val goticaImage = findViewById<ImageView>(R.id.goticaImage)

        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(goticaImage)



        val botonContinuar = findViewById<Button>(R.id.botonContinuar)
        botonContinuar.setOnClickListener {
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }
    }
}
