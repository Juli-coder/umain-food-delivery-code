package com.food.delivery.ui.screens.restaurantDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.food.delivery.utils.UiState
import com.food.domain.ResponseState
import com.food.domain.entities.Restaurant
import com.food.domain.entities.RestaurantOpenStatus
import com.food.domain.usacase.GetRestaurantOpenStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val getRestaurantOpenStatusUseCase: GetRestaurantOpenStatusUseCase
) : ViewModel() {

    private val _viewData = MutableStateFlow(ViewData())
    val viewData: StateFlow<ViewData> get() = _viewData.asStateFlow()

    fun getRestaurantOpenStatus(restaurant: Restaurant) {
        viewModelScope.launch {
            getRestaurantOpenStatusUseCase(restaurant.id).collect { response ->
                when (response) {
                    is ResponseState.Error -> {
                        _viewData.value = _viewData.value.copy(
                            state = UiState.ERROR,
                            errorMessage = response.throwable.message.orEmpty()
                        )
                    }

                    is ResponseState.Loading -> {
                        _viewData.value = _viewData.value.copy(
                            state = UiState.LOADING
                        )
                    }

                    is ResponseState.Success -> {
                        _viewData.value = _viewData.value.copy(
                            state = UiState.LOADED,
                            openStatus = response.data
                        )
                    }
                }

            }
        }
    }


    data class ViewData(
        val state: UiState = UiState.LOADING,
        val errorMessage: String = "",
        val openStatus: RestaurantOpenStatus = RestaurantOpenStatus()
    )

}