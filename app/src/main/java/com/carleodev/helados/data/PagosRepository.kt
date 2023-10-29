package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow


interface PagosRepository {

    fun getAllItemsStream(fecha:Int): Flow<List<Pagos>>
    fun getResumenPagos(fecha:Int): Flow<List<Pagos>>
    suspend fun getItemStream(id: Int): Flow<Pagos?>
    suspend fun deletePagos(idticket: Int)
    suspend fun insertItem(pagos: Pagos)
    suspend fun deleteItem(pagos: Pagos)
    suspend fun updateItem(pagos: Pagos)
}