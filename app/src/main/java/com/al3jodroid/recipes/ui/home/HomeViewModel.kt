package com.al3jodroid.recipes.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * The HomeViewModel that defines the states and data of the screen related with the list
 * of recipes available or result of a search from the server
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    //just a helper string for the log messages
    private val mTAG = HomeViewModel::class.java.simpleName

    //the StateFlow object observed, to notify UI changes in the screen
    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState


    fun callExecution() {
        if (_uiState.value is HomeUiState.Success) _uiState.value = HomeUiState.Initial
        else _uiState.value = HomeUiState.Success("Al3jodroid")
    }


}