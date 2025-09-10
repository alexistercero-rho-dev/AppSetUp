package com.rho.studio.appsetup.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SampleViewModel : ViewModel() {

    val isButtonEnabled = MutableLiveData<Boolean>(true)
    // You can add LiveData for other dynamic properties if needed

    fun onActionButtonClicked() {
        Log.d("SampleViewModel", "Action button in layout_sample_1 was clicked!")
        // Example: Disable the button after it's clicked
        isButtonEnabled.value = false
        // Add any other logic you need when the button is clicked
    }
}