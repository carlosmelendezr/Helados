package com.carleodev.helados.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


@Entity(tableName = "pagos",foreignKeys = [
    ForeignKey(entity = Ticket::class, parentColumns = arrayOf("id"), childColumns = arrayOf("idticket"))])
data class Pagos (
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false,deserialize = true)
    val id:Int = 0,
    val idticket:Int,
    val tipopago:Int,
    val monto:Double,
    @ColumnInfo
    var fecha:Int,
    @ColumnInfo
    var iditem:Int = 0
)