package com.carleodev.helados.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carleodev.helados.util.convertDateToInt

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

class SplashViewModel(savedStateHandle: SavedStateHandle
) : ViewModel() {

    val hoy = convertDateToInt(Date())
    var existeDia = 0

    init {
        viewModelScope.launch {
            //existeDia = controlBotesRepository.existeFecha(hoy).first()
        }
    }
}