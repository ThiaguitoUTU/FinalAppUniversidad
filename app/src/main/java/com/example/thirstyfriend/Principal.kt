package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class Principal : AppCompatActivity() {

    private lateinit var progressBar: CircularProgressBar
    private lateinit var tvCantidadAgua: TextView
    private lateinit var tvHistorial: TextView
    private lateinit var dbHelper: DatabaseHelper
    private var progresoActual = 0
    private val OBJETIVO_DIARIO = 1000
    private val INCREMENTO = 100 // Incremento del progreso (en mililitros)
    private val USER_ID = 1L // Ajusta el ID del usuario según tu lógica

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Inicializar base de datos
        dbHelper = DatabaseHelper(this)

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

        // Configurar la barra y cargar progreso desde la base de datos
        progressBar.setMax(OBJETIVO_DIARIO)
        cargarProgreso()

        btnMas.setOnClickListener {
            agregarAgua(INCREMENTO)
        }

        val perfilButton: Button = findViewById(R.id.perfil)
        perfilButton.setOnClickListener {
            // Crea un intent para abrir UsuarioActivity
            val intent = Intent(this, UsuarioFragment::class.java)
            intent.putExtra("USER_ID", USER_ID)
            startActivity(intent)
        }
    }

    private fun cargarProgreso() {
        // Cargar el progreso actual desde la base de datos
        progresoActual = dbHelper.getTotalAguaConsumida(USER_ID)
        progressBar.setProgress(progresoActual)
        tvCantidadAgua.text = "$progresoActual/$OBJETIVO_DIARIO ml"
    }

    private fun agregarAgua(cantidad: Int) {
        // Registrar consumo de agua
        dbHelper.registrarTomaAgua(USER_ID, cantidad)

        progresoActual += cantidad
        if (progresoActual >= OBJETIVO_DIARIO) {
            Toast.makeText(this, "¡Objetivo alcanzado! Reiniciando progreso.", Toast.LENGTH_SHORT).show()

            // Reiniciar progreso
            dbHelper.reiniciarRegistros(USER_ID)
            progresoActual = 0
        }

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
