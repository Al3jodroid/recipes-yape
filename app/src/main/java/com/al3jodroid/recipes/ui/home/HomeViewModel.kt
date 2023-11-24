package com.al3jodroid.recipes.ui.home

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

/**
 * The HomeViewModel that defines the states and data of the screen related with the list
 * of recipes available or result of a search from the server
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipesUseCase: RecipesUseCase
) : ViewModel() {
    //just a helper string for the log messages
    private val mTAG = HomeViewModel::class.java.simpleName

    //the StateFlow object observed, to notify UI changes in the screen
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState

    //the result info that going to be returned to UI
    private var resultList: List<RecipeResult>? = null

    /**
     * Called in UI when the user write some characters and start the query of recipes
     */
    fun searchRecipes(query: String) {
        Log.d(mTAG, "searching recipe: $query")
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            resultList = recipesUseCase.searchRecipes(query)
            Log.d(mTAG, "size list result server: ${resultList?.size ?: "null"}")
            onResultGetRecipes(resultList)
        }
    }

    /**
     * When the server response and the information pass through all the app layers,
     * here its validated and emit the state depending of the result.
     */
    private fun onResultGetRecipes(resultList: List<RecipeResult>?) {
        when {
            resultList == null -> _uiState.value = HomeUiState.Unavailable
            resultList.isEmpty() -> _uiState.value = HomeUiState.Empty
            else -> _uiState.value = HomeUiState.Success(resultList)
        }
    }

    /**
     * When a user click on a recipe, call this method for adding it to the local cache,
     * and use it in the next screen od details of the recipe
     */
    fun onClickRecipeFromList(recipe:RecipeResult){
        recipesUseCase.cacheRecipe(recipe)
    }
}