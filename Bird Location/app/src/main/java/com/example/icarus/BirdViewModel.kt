package com.example.icarus
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class BirdViewModel : ViewModel() {
    val birdLocation = MutableLiveData<List<BirdLocation>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchBirdData(apiKey: String, lat: Double, lng: Double, birdName: String, dist: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getBirdData(apiKey, lat, lng, birdName, dist).awaitResponse()
                if (response.isSuccessful) {
                    birdLocation.postValue(response.body())
                } else {
                    errorMessage.postValue("Failed to retrieve data")
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }
}