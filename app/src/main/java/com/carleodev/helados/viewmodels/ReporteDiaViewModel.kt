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