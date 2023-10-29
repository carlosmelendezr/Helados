package com.carleodev.helados.data

import android.content.Context

interface AppContainer {
    val itemsRepository: ItemsRepository
    val tasaRepository:TasaRepository
    val pagosRepository:PagosRepository
    val ticketRepository: TicketRepository

}


class AppDataContainer(private val context: Context ) : AppContainer {

    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(HeladosDatabase.getDatabase(context).itemDao())
    }

    override val tasaRepository: TasaRepository by lazy {
        OfflineTasaRepository(HeladosDatabase.getDatabase(context).tasaDao())
    }
    override val pagosRepository: PagosRepository by lazy {
        OfflinePagosRepository(HeladosDatabase.getDatabase(context).pagosDao())
    }

    override val ticketRepository: TicketRepository by lazy {
        OfflineTicketRepository(HeladosDatabase.getDatabase(context).ticketDao())
    }


}