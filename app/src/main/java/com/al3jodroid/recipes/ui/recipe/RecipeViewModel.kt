package com.al3jodroid.recipes.ui.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al3jodroid.recipes.interactor.RecipesUseCase
import com.al3jodroid.recipes.model.data.RecipeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipesUseCase: RecipesUseCase
) : ViewModel() {
    //just a helper string for the log messages
    private val mTAG = RecipeViewModel::class.java.simpleName

    //the StateFlow object observed, to notify UI changes in the screen
    private val _uiState: MutableStateFlow<RecipeUiState> = MutableStateFlow(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState

    //the result info that going to be returned to UI
    private var result: RecipeResult? = null


    fun getRecipeById(id: String) {
        Log.d(mTAG, "searching recipe Id: $id")
        viewModelScope.launch(Dispatchers.IO) {
            result = recipesUseCase.getRecipeById(id)
            Log.d(mTAG, "recipe found: ${result ?: "null"}")
            onResultGetRecipe(result)
        }
    }

    private fun onResultGetRecipe(result: RecipeResult?) {
        when (result) {
            null -> _uiState.value = RecipeUiState.Unavailable
            else -> _uiState.value = RecipeUiState.Success(result)
        }
    }
}