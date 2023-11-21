package com.carleodev.helados.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {

    @Query("SELECT * from ticket WHERE fecha =:fecha ORDER BY id DESC")
    fun getAllItems(fecha:Int): Flow<List<Ticket>>

    @Query("SELECT ticket.id," +
            "ticket.iditem," +
            "ticket.cant," +
            "ticket.valordolar," +
            "ticket.valorbs," +
            "ticket.fecha," +
            "ticket.hora," +
            "ticket.iditem," +
            "ticket.anulado, " +
            "ticket.sabor, " +
            "ticket.toping, " +
            "ticket.lluvia, " +
            "ticket.pago " +
            "FROM ticket " +
            "LEFT JOIN items ON ticket.iditem=items.id " +
            "WHERE ticket.id = :id " )
    fun getItem(id: Int): Flow<Ticket>

    @Query("SELECT SUM(cant) as total FROM ticket " +
            "WHERE fecha =:fecha AND " +
            "anulado=:anulado")
    fun getTotalCant( fecha:Int, anulado:Boolean=false): Flow<Int>

    @Query("SELECT SUM(valordolar) as totaldolar FROM ticket " +
            "WHERE fecha =:fecha AND " +
            "anulado=:anulado")
    fun getTotalDolar( fecha:Int, anulado:Boolean=false): Flow<Double>

    @Query("SELECT SUM(valorbs) as totalbs FROM ticket " +
            "WHERE fecha =:fecha AND " +
            "anulado=:anulado")
    fun getTotalBs( fecha:Int, anulado:Boolean=false): Flow<Double>

    @Query("SELECT SUM(cant) as totcant FROM ticket " +
            "WHERE fecha =:fecha AND " +
            "anulado=:anulado")
    fun getSumaAnulado( fecha:Int, anulado:Boolean=true): Flow<Int>

    @Query("SELECT " +
            "SUM(valorbs) as valorbs " +
            "FROM ticket " +
            "WHERE fecha =:fecha AND pago=:pago " +
            " AND anulado=:anulado ")
    fun getTotalPagosBs( fecha:Int, pago:String, anulado:Boolean=false): Flow<Double>

    @Query("SELECT " +
            "SUM(valordolar) as valordolar " +
            "FROM ticket " +
            "WHERE fecha =:fecha AND pago=:pago " +
            " AND anulado=:anulado ")
    fun getTotalPagosDolar( fecha:Int, pago:String, anulado:Boolean=false): Flow<Double>


    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tickets: Ticket):Long

    @Update
    suspend fun update(tickets: Ticket)

    @Delete
    suspend fun delete(tickets: Ticket)
}