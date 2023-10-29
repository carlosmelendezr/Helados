package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

class OfflineTicketRepository(private val ticketsDao: TicketDao): TicketRepository {
    override fun getAllItemsStream(fecha: Int): Flow<List<Ticket>> = ticketsDao.getAllItems(fecha)
    override fun getItemStream(id: Int): Flow<Ticket?> = ticketsDao.getItem(id)

    override fun getItem(id: Int): Flow<Ticket>  = ticketsDao.getItem(id)
    override fun getSumaBote(iditem: Int, fecha:Int, anulado:Boolean): Flow<Int> =
        ticketsDao.getSumaBote(iditem, fecha, anulado)
    override fun getSumaBoteAnulado( fecha:Int, anulado:Boolean): Flow<Int> =
        ticketsDao.getSumaBoteAnulado(fecha)
    override suspend fun insertItem(tickets: Ticket):Long = ticketsDao.insert(tickets)
    override suspend fun deleteItem(tickets: Ticket) = ticketsDao.delete(tickets)
    override suspend fun updateItem(tickets: Ticket) = ticketsDao.update(tickets)

}