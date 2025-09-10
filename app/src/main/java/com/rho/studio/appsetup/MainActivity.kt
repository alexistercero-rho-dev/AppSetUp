package com.rho.studio.appsetup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView // Also add this for the TextView you are using
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.rho.studio.appsetup.activities.ResultActivity
import com.rho.studio.appsetup.ui.theme.AppSetUpTheme

class MainActivity : ComponentActivity() {
    // Removed: private lateinit var binding: ActivityMainBinding (as it's unused)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSetUpTheme {
                MainScreen() // Extract content into a dedicated Composable
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        // Column to arrange Compose and XML content vertically
        Column(modifier = Modifier.padding(innerPadding)) {
            // The bridge: Adding your XML view
            AndroidView(
                factory = { context ->
                    // Option 1: Using LayoutInflater (as you had)
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_sample_1, null, false)

                    // Option 2: Using View.inflate (more concise if preferred)
                    // View.inflate(context, R.layout.layout_sample_1, null)
                },
                modifier = Modifier.fillMaxWidth(), // Use Compose modifiers for layout
                update = { view ->
                    val button = view.findViewById<Button>(R.id.action_button)
                    val textView = view.findViewById<TextView>(R.id.text_view)
                    val context = view.context

                    button.setOnClickListener {
                        Log.d("MainActivity", "action_button clicked!") // <-- ADD THIS LOG

                        val messageText = context.getString(R.string.button_clicked_compose_message)
                        textView.text = messageText

                        val intent = ResultActivity.newIntent(context, messageText)
                        Log.d("MainActivity", "Starting ResultActivity with message: $messageText") // <-- ADD THIS LOG
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e("MainActivity", "Error starting ResultActivity", e) // <-- ADD THIS LOG FOR ERRORS
                        }
                    }
                }
            )
            // You can add more Jetpack Compose UI elements here if needed
            // Text("Another Compose element below the XML view")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppSetUpTheme {
        MainScreen() // Preview the main content
    }
}