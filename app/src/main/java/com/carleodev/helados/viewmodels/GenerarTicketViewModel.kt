package com.carleodev.helados.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.ItemsRepository
import com.carleodev.helados.data.Ticket
import com.carleodev.helados.data.TicketRepository
import com.carleodev.helados.print.ImprimirRecibo
import com.carleodev.helados.ui.home.CrearItemDestination
import com.carleodev.helados.util.convertDateToInt
import com.mazenrashed.printooth.Printooth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date


class GenerarTicketViewModel(savedStateHandle: SavedStateHandle,
                         private val itemsRepository: ItemsRepository,
                         private val ticketRepository: TicketRepository,
                         private val context: Context): ViewModel() {

    val hoy = convertDateToInt(Date())
    var itemId: Int = checkNotNull(savedStateHandle[CrearItemDestination.itemIdArg])
    var itemUIState by mutableStateOf(ItemUIState())
    var ticketUIState by mutableStateOf(TicketUIState(toping  = mutableSetOf(),lluvia= setOf()))


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

   /* fun suma() {
        ticketUIState = ticketUIState(cant = (ticketUIState.cant.toInt()+1).toString()))
    }*/
    fun updateUiState(pticketUIState:TicketUIState) {
        ticketUIState = pticketUIState
    }

    fun guardarTicket() {

        if (ticketUIState.sabor.isBlank()) return

        if (!ticketUIState.formapago) {
            ticketUIState = ticketUIState.copy(formapago = true)
            return
        }

        var lastId:Long =0

        val ticket = Ticket(
            fecha = hoy,
            iditem = itemId, hora = 0,
            cant=ticketUIState.cant.toInt(), sabor = ticketUIState.sabor,
            toping = ticketUIState.toping.toString(),
            lluvia = ticketUIState.lluvia.toString(),
            pago = ticketUIState.pago,
            valordolar=itemUIState.price.toDouble(),
            valorbs = itemUIState.preciobs.toDouble()

        )

        viewModelScope.launch {

            lastId = ticketRepository.insertItem(ticket)

            Printooth.init(context);
            ImprimirRecibo(context,ticket.copy(id=lastId.toInt()), itemUIState.toItem())

        }

    }

}

data class TicketUIState(
    val sabor:String="",
    val toping:MutableSet<String> ,
    val lluvia:Set<String> ,
    val pago:String="",
    val formapago:Boolean=false,
    var cant:String="1"
) {

    fun suma() {
        cant = (cant.toInt()+1).toString()
    }
    fun resta() {
        if (cant.toInt()>0) {
            cant = (cant.toInt() - 1).toString()
        }
    }


    fun agregarToping(texto:String) {
        toping.add(texto)
    }
    fun borrarToping(texto:String) {
        toping.remove(texto)
    }
}

