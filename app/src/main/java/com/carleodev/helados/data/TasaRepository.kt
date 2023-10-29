package com.carleodev.helados.data

import kotlinx.coroutines.flow.Flow


interface TasaRepository {
    fun getItemStream(id: Int): Flow<Tasa?>
    suspend fun insertItem(tasa: Tasa)
    suspend fun updateItem(tasa: Tasa)
}