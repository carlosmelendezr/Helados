package com.carleodev.helados.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository
import com.carleodev.helados.data.OfflineItemsRepository
import kotlinx.coroutines.runBlocking


class CrearItemViewModel(savedStateHandle: SavedStateHandle,
                         private val itemsRepository: ItemsRepository

) : ViewModel() {
    var datosValidos = false
    var itemUIState by mutableStateOf(ItemUIState())

    fun updateUiState(pitemUIState:ItemUIState) {
        itemUIState = pitemUIState

    }

    fun guardar() {
        runBlocking {
            //itemsRepository.insertItem(itemUIState.toItem())


        }

    }

    fun validate():Boolean {
        return itemUIState.descrip.isNotBlank() &&
                itemUIState.price.isNotBlank()
    }
}

data class ItemUIState(
    val id: String = "",
    val descrip: String="",
    val price: String="",
    val cantidad: String="",
    val imagen:String="",
    val estatus:String="",
    val isValid:Boolean=false
)


fun ItemUIState.toItem():Item = Item(
    id = id.toInt(),
    descrip = descrip,
    price = price.toDouble(),
    cantidad = cantidad.toInt(),
    imagen = imagen,
    estatus = estatus.toInt()
)

fun Item.toItemUIState():ItemUIState= ItemUIState(
    id = id.toString(),
    descrip = descrip,
    price = price.toString(),
    cantidad = cantidad.toString(),
    imagen = imagen,
    estatus = estatus.toString()
)