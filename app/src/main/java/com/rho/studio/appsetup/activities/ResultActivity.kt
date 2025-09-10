package com.rho.studio.appsetup.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
// Removed android.widget.TextView as it's not directly used if ViewBinding is complete
import androidx.activity.enableEdgeToEdge // Keep this import
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// Removed com.rho.studio.appsetup.R as it's not directly used if ViewBinding is complete
import com.rho.studio.appsetup.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Call super.onCreate() first
        super.onCreate(savedInstanceState)
        Log.d("ResultActivity", "onCreate: super.onCreate called")

        // 2. Enable edge-to-edge
        enableEdgeToEdge()
        Log.d("ResultActivity", "onCreate: Edge-to-edge enabled")

        // 3. Inflate layout and set content view
        try {
            binding = ActivityResultBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d("ResultActivity", "onCreate: Content view set")

            val message = intent.getStringExtra(EXTRA_MESSAGE).orEmpty()
            Log.d("ResultActivity", "onCreate: Message received: '$message'")

            binding.messageTextView.text = message
            Log.d("ResultActivity", "onCreate: Message set to TextView")

            // 4. Set up window insets listener
            // Ensure the root view in your activity_result.xml has android:id="@+id/main"
            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, windowInsets ->
                Log.d("ResultActivity", "onCreate: Applying window insets")
                val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                // It's important to return the insets, whether they were consumed or not.
                windowInsets
            }
            Log.d("ResultActivity", "onCreate: Finished successfully")

        } catch (e: Exception) {
            Log.e("ResultActivity", "onCreate: CRASHED", e)
            // It's often helpful to re-throw the exception during development to see the full stack trace
            // in the system logs, which can make it easier to identify the root cause if it's not obvious.
            throw e
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "com.rho.studio.appsetup.activities.MESSAGE"

        fun newIntent(context: Context, message: String?): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
        }
    }
}
