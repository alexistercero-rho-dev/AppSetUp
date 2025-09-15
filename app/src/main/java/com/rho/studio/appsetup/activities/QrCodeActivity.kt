package com.rho.studio.appsetup.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.rho.studio.appsetup.databinding.ActivityQrCodeBinding // Import ViewBinding class
import com.rho.studio.appsetup.R

class QrCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrCodeBinding

    companion object {
        private const val EXTRA_QR_DATA = "com.rho.studio.appsetup.activities.EXTRA_QR_DATA"

        /**
         * Creates an Intent to start QrCodeActivity.
         * @param context The context from which the activity is started.
         * @param qrData The string data to be encoded into the QR code.
         * @return An Intent configured to start QrCodeActivity with the provided data.
         */
        fun newIntent(context: Context, qrData: String): Intent {
            val intent = Intent(context, QrCodeActivity::class.java)
            intent.putExtra(EXTRA_QR_DATA, qrData)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the data to encode from the intent
        val stringDataToEncode = intent.getStringExtra(EXTRA_QR_DATA) ?: "Error: No data provided"

        // Generate and display the QR Code
        if (stringDataToEncode != "Error: No data provided") {
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                    stringDataToEncode,
                    BarcodeFormat.QR_CODE,
                    600, // width in pixels
                    600  // height in pixels
                )
                binding.qrCodeImageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                binding.qrCodeImageView.setImageResource(R.drawable.ic_broken_image) // Placeholder for error
                // Optionally, display the error message in a TextView or Toast
            }
        } else {
            // Handle the case where no data was provided, e.g., show an error message
            // For now, let's assume qrCodeImageView can display text or you have another TextView for errors.
            // If qrCodeImageView is purely for images, you'd show a placeholder or use a separate TextView.
            // As a simple fallback, you could try to encode the error message itself, or show a placeholder.
            binding.qrCodeImageView.setImageResource(R.drawable.ic_broken_image) // Placeholder for no data
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
