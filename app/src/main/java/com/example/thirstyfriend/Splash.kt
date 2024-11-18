package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Cargar el GIF usando Glide
        val characterImageView = findViewById<ImageView>(R.id.splashImage)
        Glide.with(this)
            .asGif()
            .load(R.drawable.animation) // Reemplaza con el nombre de tu GIF en drawable
            .into(characterImageView)

        // Usar corrutina para el retardo
        GlobalScope.launch {
            delay(3000) // 2000 milisegundos = 2 segundos
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }
    }
}
