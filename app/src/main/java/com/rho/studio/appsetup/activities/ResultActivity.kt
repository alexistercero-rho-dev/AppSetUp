package com.rho.studio.appsetup.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rho.studio.appsetup.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        //val view1 = findViewById(R.id.main)
        val message = intent.extras?.getString("message").orEmpty()

        //This pattern is crucial for creating well-behaved,
        // modern Android UIs that adapt gracefully to different screen configurations and system UI states,
        // especially when implementing edge-to-edge designs.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
            v, insets ->
            //Gets the insets for the status bar and navigation bar.
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            //Gets the insets for the software keyboard.
            //insets.getInsets(WindowInsetsCompat.Type.ime())

            //This pushes the content of the view inwards, away from the system bars,
            // ensuring it remains visible and interactive. You might also adjust margins or other layout parameters.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}