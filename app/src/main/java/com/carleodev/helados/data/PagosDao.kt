package com.carleodev.helados.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PagosDao {

    @Query("SELECT pagos.id,pagos.idticket,pagos.tipopago,pagos.monto,ticket.iditem, ticket.fecha from pagos " +
            "LEFT JOIN ticket ON pagos.idticket=ticket.id "+
            "WHERE pagos.fecha = :fecha ")

    fun getAllItems(fecha:Int): Flow<List<Pagos>>
    @Query("SELECT pagos.id,pagos.idticket,pagos.tipopago,SUM(pagos.monto) as monto,ticket.fecha,ticket.iditem  from pagos " +
            "LEFT JOIN ticket ON pagos.idticket=ticket.id "+
            "WHERE pagos.fecha = :fecha GROUP BY tipopago ")
    fun getResumenPagos(fecha:Int): Flow<List<Pagos>>

    @Query("SELECT * from pagos WHERE id = :id")
    fun getItem(id: Int): Flow<Pagos>

    @Query("DELETE from pagos WHERE idticket = :idticket")
    suspend fun deletePagos(idticket: Int)


    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pagos: Pagos)

    @Update
    suspend fun update(pagos: Pagos)

    @Delete
    suspend fun delete(pagos: Pagos)
}