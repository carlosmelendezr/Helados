package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getAllItemsStream(fecha: Int): Flow<List<Ticket>>

    fun getItemStream(id: Int): Flow<Ticket?>

    fun getItem(id: Int): Flow<Ticket>

    fun getSumaBote(iditem: Int, fecha:Int, anulado:Boolean=false):Flow<Int>

    fun getSumaBoteAnulado( fecha:Int, anulado:Boolean=true): Flow<Int>

    suspend fun insertItem(tickets: Ticket):Long

    suspend fun deleteItem(tickets: Ticket)

    suspend fun updateItem(tickets: Ticket)
}