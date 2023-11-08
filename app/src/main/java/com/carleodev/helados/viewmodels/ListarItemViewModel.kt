package com.carleodev.helados.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository
import com.carleodev.helados.data.TasaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking


class ListarItemViewModel(savedStateHandle: SavedStateHandle,
                         private val itemsRepository: ItemsRepository

) : ViewModel() {
    var tasaDia by mutableStateOf("")
    var tasaDolar:Double=0.0

    val listaUiState: StateFlow<ListaUiState> =
        itemsRepository.getAllItemsStream().map { ListaUiState(it)  }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListaUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun updateTasa(tasa:String) {
        if (tasa.isNotBlank()) {
            tasaDia = tasa
            tasaDolar = tasa.toDoubleOrNull()?:0.0
        }

    }

    fun deleteLista(item: Item) {
        viewModelScope.launch {
            itemsRepository.deleteItem(item)
        }

    }




}

data class ListaUiState(val listas: List<Item> = listOf())