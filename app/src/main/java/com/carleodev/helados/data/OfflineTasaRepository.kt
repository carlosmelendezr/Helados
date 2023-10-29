package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow

class OfflineTasaRepository(private val tasaDao: TasaDao) : TasaRepository {
    override fun getItemStream(id: Int): Flow<Tasa?> = tasaDao.getItem(id)
    override suspend fun insertItem(tasa: Tasa) = tasaDao.insert(tasa)
    override suspend fun updateItem(tasa: Tasa) = tasaDao.update(tasa)
}