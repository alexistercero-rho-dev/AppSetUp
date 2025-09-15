package com.rho.studio.appsetup

//import android.content.Intent
//import android.annotation.SuppressLint
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
//import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.rho.studio.appsetup.activities.QrCodeActivity
//import com.rho.studio.appsetup.activities.ResultActivity
import com.rho.studio.appsetup.ui.theme.AppSetUpTheme

class MainActivity : ComponentActivity() {
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
            // The bridge: Adding Compose content to the XML view
            AndroidView(
                factory = { context ->
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_sample_1, null, false)
                },
                modifier = Modifier.fillMaxWidth(), // Use Compose modifiers for layout
                update = { view ->
                    val button = view.findViewById<Button>(R.id.action_button)
                    val textView = view.findViewById<TextView>(R.id.text_view)
                    val context = view.context // Get context from the view

                    button.setOnClickListener {
                        Log.d("MainScreen", "action_button clicked!")

                        // 1. Update the TextView in the XML layout
                        val messageForTextView = "Preparing QR Code..." // Or use a string resource
                        textView.text = messageForTextView
                        Log.d("MainScreen", "TextView updated to: $messageForTextView")

                        // 2. Define the data to be encoded in the QR code.
                        // Let's use a specific string for the QR data, or you can use textView.text.toString()
                        // if you want the updated text view content to be encoded.
                        val dataToEncodeInQr = "https://rho.studio/"
                        // Or, if you want to encode what you just put in the text view:
                        // val dataToEncodeInQr = messageForTextView

                        Log.d("MainScreen", "Data to encode in QR: $dataToEncodeInQr")

                        // 3. Create Intent for QrCodeActivity using its factory method
                        val qrIntent = QrCodeActivity.newIntent(context, dataToEncodeInQr)
                        Log.d("MainScreen", "Starting QrCodeActivity with data: $dataToEncodeInQr")

                        // 4. Start QrCodeActivity
                        try {
                            context.startActivity(qrIntent)
                        } catch (e: Exception) {
                            Log.e("MainScreen", "Error starting QrCodeActivity", e)
                        }
                    }
                }
            )
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