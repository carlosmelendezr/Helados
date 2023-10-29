package com.carleodev.helados

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.carleodev.helados.viewmodels.CrearItemViewModel
import com.carleodev.helados.viewmodels.SplashViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            SplashViewModel(
                this.createSavedStateHandle()

            )
        }

        initializer {
            CrearItemViewModel(
                this.createSavedStateHandle()

            )
        }

    }


    fun CreationExtras.heladosApplication(): HeladosApplication =
        (this[AndroidViewModelFactory.APPLICATION_KEY] as HeladosApplication)
}