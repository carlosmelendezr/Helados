package com.carleodev.helados.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository
import com.carleodev.helados.data.TasaRepository

import kotlinx.coroutines.runBlocking


class CrearItemViewModel(savedStateHandle: SavedStateHandle,
                         private val itemsRepository: ItemsRepository

) : ViewModel() {
    var datosValidos = false
    var itemUIState by mutableStateOf(ItemUIState())

    /*var capturedImageUri by
        mutableStateOf<Uri>(Uri.EMPTY)*/


    fun updateUiState(pitemUIState:ItemUIState) {
        itemUIState = pitemUIState

    }

    fun guardar() {
        runBlocking {
           // itemUIState = itemUIState.copy(imagen=capturedImageUri.toString())
            itemsRepository.insertItem(itemUIState.toItem())

        }

    }

    fun validate():Boolean {
        return itemUIState.descrip.isNotBlank() &&
                itemUIState.price.isNotBlank()
    }
}

data class ItemUIState(
    val id: Int =0,
    val descrip: String="",
    val price: String="",
    val cantidad: String="",
    val imagen:String="",
    val estatus:String="",
    val isValid:Boolean=false
)


fun ItemUIState.toItem():Item = Item(
    id = id,
    descrip = descrip,
    price =  price.toDoubleOrNull() ?: 0.0,
    cantidad = cantidad.toIntOrNull() ?: 0,
    imagen = imagen,
    estatus = estatus.toIntOrNull() ?: 0
)

fun Item.toItemUIState():ItemUIState= ItemUIState(
    id = id,
    descrip = descrip,
    price = price.toString(),
    cantidad = cantidad.toString(),
    imagen = imagen,
    estatus = estatus.toString()
)