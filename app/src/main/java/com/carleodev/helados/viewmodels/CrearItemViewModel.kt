package com.carleodev.helados.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository

class CrearItemViewModel(savedStateHandle: SavedStateHandle

) : ViewModel() {
    var datosValidos = false
    //var crearDiaUIState by mutableStateOf(CrearItemUIState())

    /*fun updateUiState(datosBotes: DatosBotes) {
        crearDiaUIState = CrearDiaUIState(datosBotes, isValid = validate(datosBotes))

    }*/

    fun guardar() {

        /*runBlocking {
            itemsRepository.insertItem(Item(id=1,
                price=crearDiaUIState.datosBotes.precioAzul.toDouble(),
                cantidad=crearDiaUIState.datosBotes.azul.toInt(),
                puestos=4,color=1,estatus=0, descrip = "BOTE AZUL"))


        }*/

    }

    fun validate(item:Item):Boolean {


        return datosValidos
    }


}

data class CrearItemUIState(val item: Item,
                           val isValid:Boolean=false)

