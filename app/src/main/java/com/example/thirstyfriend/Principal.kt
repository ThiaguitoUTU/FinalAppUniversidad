package com.example.thirstyfriend

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class Principal : AppCompatActivity() {

    private lateinit var progressBar: CircularProgressBar
    private lateinit var tvCantidadAgua: TextView
    private lateinit var tvHistorial: TextView
    private var progresoActual = 0
    private val OBJETIVO_DIARIO = 1000
    private val INCREMENTO = 100 // Incremento del progreso (en mililitros)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Ajustar márgenes para insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val characterImageView = findViewById<ImageView>(R.id.characterImageView)

        // Carga de animación con Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(characterImageView)

        // Referenciar vistas
        progressBar = findViewById(R.id.progressBar)
        tvCantidadAgua = findViewById(R.id.tvCantidadAgua)
        tvHistorial = findViewById(R.id.tvHistorial)
        val btnMas: ImageButton = findViewById(R.id.btnMas)

        // Configurar la barra
        progressBar.setMax(OBJETIVO_DIARIO)

        // Configurar evento de clic
        btnMas.setOnClickListener {
            agregarAgua(INCREMENTO)
        }
    }

    private fun agregarAgua(cantidad: Int) {
        if (progresoActual + cantidad <= OBJETIVO_DIARIO) {
            progresoActual += cantidad

            // Actualizar la barra circular
            progressBar.setProgress(progresoActual)

            // Actualizar texto
            tvCantidadAgua.text = "$progresoActual/$OBJETIVO_DIARIO ml"

            // Actualizar historial
            val horaActual = java.text.DateFormat.getTimeInstance().format(java.util.Date())
            val nuevoRegistro = "$horaActual - ${cantidad}ml\n${tvHistorial.text}"
            tvHistorial.text = nuevoRegistro
        }
    }
}
