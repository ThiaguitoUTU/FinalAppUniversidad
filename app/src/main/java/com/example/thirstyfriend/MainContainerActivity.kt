package com.example.thirstyfriend

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container) // Este es el layout principal

        // Verificar si es la primera vez que se carga la actividad
        if (savedInstanceState == null) {
            // Cargar el primer fragmento
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainActivity2()) // MainActivity como primer fragmento
                .commit()
        }
    }
}
