package com.carleodev.helados.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TasaDao {

    @Query("SELECT * from tasa ")
    fun getAllItems(): Flow<List<Tasa>>

    @Query("SELECT * from tasa WHERE id = :id")
    fun getItem(id: Int): Flow<Tasa>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tasa: Tasa)

    @Update
    suspend fun update(tasa: Tasa)

    @Delete
    suspend fun delete(tasa: Tasa)
}