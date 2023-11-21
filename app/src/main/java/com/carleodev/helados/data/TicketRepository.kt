package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getAllItemsStream(fecha: Int): Flow<List<Ticket>>

    fun getItemStream(id: Int): Flow<Ticket?>

    fun getItem(id: Int): Flow<Ticket>

    fun getTotalCant( fecha:Int, anulado:Boolean=false):Flow<Int>
    fun getTotalDolar( fecha:Int, anulado:Boolean=false):Flow<Double>
    fun getTotalBs( fecha:Int, anulado:Boolean=false):Flow<Double>
    fun getTotalPagos( fecha:Int, anulado:Boolean=false):Flow<Ticket>

    fun getSumaAnulado( fecha:Int, anulado:Boolean=true): Flow<Int>

    suspend fun insertItem(tickets: Ticket):Long

    suspend fun deleteItem(tickets: Ticket)

    suspend fun updateItem(tickets: Ticket)
}