package com.carleodev.helados.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository
import com.carleodev.helados.ui.home.CrearItemDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking


class CrearItemViewModel(savedStateHandle: SavedStateHandle,
                         private val itemsRepository: ItemsRepository

) : ViewModel() {

    var itemId: Int = checkNotNull(savedStateHandle[CrearItemDestination.itemIdArg])

    var itemUIState by mutableStateOf(ItemUIState())


    init {
        if (itemId>0) {
            viewModelScope.launch {
                itemUIState = itemsRepository.getItemStream(itemId)
                    .filterNotNull()
                    .first()
                    .toItemUIState()
            }
        }
    }


    fun updateUiState(pitemUIState:ItemUIState) {
        itemUIState = pitemUIState
        itemUIState.isValid=validate(pitemUIState)


    }

    fun guardar() {

        runBlocking {
            if (itemId==0) {
                itemsRepository.insertItem(itemUIState.toItem())
            } else {
                itemsRepository.updateItem(itemUIState.toItem())
            }

        }

    }


}

data class ItemUIState(
    val id: Int =0,
    val descrip: String="",
    val price: String="",
    val preciobs: String="",
    val cantidad: String="",
    val imagen: Bitmap? = null,
    val estatus:String="",
    var isValid:Boolean=false
)

fun validate(itemUIState:ItemUIState):Boolean {
    return itemUIState.descrip.isNotBlank() &&
            itemUIState.price.isNotBlank() &&
            itemUIState.imagen!=null
}


fun ItemUIState.toItem():Item = Item(
    id = id,
    descrip = descrip,
    price =  price.toDoubleOrNull() ?: 0.0,
    preciobs = preciobs.toDoubleOrNull() ?: 0.0,
    cantidad = cantidad.toIntOrNull() ?: 0,
    imagen = imagen,
    estatus = estatus.toIntOrNull() ?: 0
)

fun Item.toItemUIState():ItemUIState= ItemUIState(
    id = id,
    descrip = descrip,
    price = price.toString(),
    preciobs = preciobs.toString(),
    cantidad = cantidad.toString(),
    imagen = imagen,
    estatus = estatus.toString()
)