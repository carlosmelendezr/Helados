package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

class OfflineTicketRepository(private val ticketsDao: TicketDao): TicketRepository {
    override fun getAllItemsStream(fecha: Int): Flow<List<Ticket>> = ticketsDao.getAllItems(fecha)
    override fun getItemStream(id: Int): Flow<Ticket?> = ticketsDao.getItem(id)

    override fun getItem(id: Int): Flow<Ticket>  = ticketsDao.getItem(id)
    override fun getTotalCant( fecha:Int, anulado:Boolean): Flow<Int> =
        ticketsDao.getTotalCant(fecha, anulado)
    override fun getTotalBs( fecha: Int, anulado: Boolean): Flow<Double>
    = ticketsDao.getTotalBs(fecha,anulado)
    override fun getTotalDolar(fecha: Int, anulado: Boolean): Flow<Double>
            = ticketsDao.getTotalDolar(fecha,anulado)

    override fun getTotalPagos(fecha: Int, anulado: Boolean): Flow<Ticket>
    = ticketsDao.getTotalPagos(fecha,anulado)
    override fun getSumaAnulado( fecha:Int, anulado:Boolean): Flow<Int> =
        ticketsDao.getSumaAnulado(fecha)
    override suspend fun insertItem(tickets: Ticket):Long = ticketsDao.insert(tickets)
    override suspend fun deleteItem(tickets: Ticket) = ticketsDao.delete(tickets)
    override suspend fun updateItem(tickets: Ticket) = ticketsDao.update(tickets)

}