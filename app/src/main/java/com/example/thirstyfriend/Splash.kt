package com.example.thirstyfriend

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Mostrar el GIF usando Glide
        val splashImageView = findViewById<ImageView>(R.id.splashImage)
        Glide.with(this)
            .asGif()
            .load(R.drawable.animation)
            .into(splashImageView)

        // Usar corrutina para retrasar el inicio
        lifecycleScope.launch {
            delay(3000) // 3 segundos
            startActivity(Intent(this@Splash, MainContainerActivity::class.java))
            finish() // Cierra la actividad Splash
        }
    }
}
