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

            // Verificar si el usuario ya completó el onboarding
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val onboardingCompleted = prefs.getBoolean("onboarding_completed", false)

            val intent = if (onboardingCompleted) {
                // Si ya completó el onboarding, ir a Principal
                Intent(this@Splash, Principal::class.java)
            } else {
                // Si no ha completado el onboarding, ir a MainActivity
                Intent(this@Splash, MainActivity::class.java)
            }

            startActivity(intent)
            finish()
        }
    }
}
