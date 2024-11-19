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

class MainActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_main, container, false)

        // Configurar la animación
        val characterImageView = view.findViewById<ImageView>(R.id.characterImageView)
        Glide.with(requireContext())
            .asGif()
            .load(R.drawable.animation)
            .into(characterImageView)

        // Configurar el botón
        view.findViewById<Button>(R.id.startButton).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainActivity2())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

