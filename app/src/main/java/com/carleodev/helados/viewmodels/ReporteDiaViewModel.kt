package com.carleodev.helados.viewmodels


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carleodev.helados.data.Ticket
import com.carleodev.helados.data.TicketRepository
import com.carleodev.helados.util.convertDateToInt

import kotlinx.coroutines.flow.*
import java.util.*

class ReporteDiaViewModel(savedStateHandle: SavedStateHandle,
                          private val ticketRepository: TicketRepository,

                          ) : ViewModel() {
    val hoy = convertDateToInt(Date())

    val totalCant:StateFlow<Int> = ticketRepository.getTotalCant(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0)

    val totalDolar:StateFlow<Double> = ticketRepository.getTotalDolar(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)

    val totalBs:StateFlow<Double> = ticketRepository.getTotalBs(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)

    val totalPagosBs:StateFlow<Ticket> = ticketRepository.getTotalPagos(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = Ticket(id=0,cant=0,fecha=0,
            iditem = 0,hora=0, valordolar = 0.0, valorbs = 0.0,
            sabor="", toping = "",lluvia="",pago="",anulado=false))


    /* val listaPagos: StateFlow<PagosUiState> =
         pagosRepository.getResumenPagos(hoy).map { PagosUiState(it) }
             .stateIn(
                 scope = viewModelScope,
                 started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                 initialValue = PagosUiState())*/


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

    }


}

data class PagosUiState(val itemList: List<Ticket> = listOf())