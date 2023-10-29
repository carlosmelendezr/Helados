package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

class OfflinePagosRepository(private val pagosDao: PagosDao) : PagosRepository {
    override fun getAllItemsStream(fecha:Int): Flow<List<Pagos>> = pagosDao.getAllItems(fecha)
    override fun getResumenPagos(fecha:Int): Flow<List<Pagos>> = pagosDao.getResumenPagos(fecha)
    override suspend fun getItemStream(id: Int): Flow<Pagos?> = pagosDao.getItem(id)
    override suspend fun insertItem(pagos: Pagos) = pagosDao.insert(pagos)
    override suspend fun deletePagos(idticket: Int) = pagosDao.deletePagos(idticket)
    override suspend fun deleteItem(pagos: Pagos) = pagosDao.delete(pagos)
    override suspend fun updateItem(pagos: Pagos) = pagosDao.update(pagos)
}