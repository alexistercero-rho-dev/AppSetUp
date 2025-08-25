package com.rho.studio.appsetup

import android.os.Bundle
import android.view.View
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
import com.rho.studio.appsetup.ui.theme.AppSetUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSetUpTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Column to arrange Compose and XML content vertically
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // 2. The bridge: Adding your XML view
                        AndroidView(
                            factory = { context ->
                                // This block inflates your traditional XML layout
                                LayoutInflater.from(context)
                                    .inflate(R.layout.layout_sample_1, null, false)
                            },
                            modifier = Modifier.fillMaxWidth(), // Use Compose modifiers for layout
                            update = { view ->
                                // This block runs after inflation and when state changes.
                                // You can access the inflated view and set up listeners.
                                val button = view.findViewById<Button>(R.id.action_button)
                                val textView = view.findViewById<TextView>(R.id.text_view)

                                button.setOnClickListener {
                                    textView.text = "Button was clicked from Compose!"
                                    // You can also call functions on your ViewModel here
                                    // viewModel.updateData()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppSetUpTheme {
        Greeting("Android")
    }
}