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

   /* val totalDolar:StateFlow<Double> = ticketRepository.getTotalDolar(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)

    val totalBs:StateFlow<Double> = ticketRepository.getTotalBs(hoy).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)*/

    val totalBsEfectivo:StateFlow<Double> = ticketRepository.getTotalPagosBs(hoy,"Efectivo Bs").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)

    val totalTarjeta:StateFlow<Double> = ticketRepository.getTotalPagosBs(hoy,"Tarjeta").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)

    val totalPagoMovil:StateFlow<Double> = ticketRepository.getTotalPagosBs(hoy,"Pago Movil").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)


    val totalDolarEfectivo:StateFlow<Double> = ticketRepository.getTotalPagosDolar(hoy,"Efectivo Dolar").stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = 0.0)



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

data class ListaPagoUiState(val itemList: List<Ticket> = listOf())

