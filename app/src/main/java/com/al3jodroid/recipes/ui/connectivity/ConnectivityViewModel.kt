package com.al3jodroid.recipes.ui.connectivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al3jodroid.recipes.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver //emit states related with the network changes
) : ViewModel() {
    //just a helper string for the log messages
    private val mTAG = ConnectivityViewModel::class.java.simpleName

    //the LiveData object observed by the Fragment for notify UI changes to the user
    private val _uiState: MutableStateFlow<ConnectivityUiState> =
        MutableStateFlow(ConnectivityUiState.UnknownNetwork) //init as null, but null is also managed at UI
    val uiState: StateFlow<ConnectivityUiState> = _uiState

    /**
     * Init the listening from the network connectivity changes
     * and emit states ready to read in UI
     */
    fun startListenConnectivity() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collect { uiState ->
                Log.d(mTAG, uiState.name)

                when (uiState) {
                    ConnectivityObserver.Status.Available ->{
                        _uiState.emit(ConnectivityUiState.ConnectedNetwork)
                    }
                    ConnectivityObserver.Status.Unavailable ->{
                        _uiState.emit(ConnectivityUiState.DisconnectedNetwork)
                    }
                }
            }
        }
    }
}