package com.carleodev.helados.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false,deserialize = true)
    val id: Int = 0,
    val fecha: Int,
    val iditem: Int,
    val hora: Int,
    val cant:Int,
    val sabor:String,
    val toping:String,
    val lluvia:String,
    val pago:String,
    val anulado: Boolean = false,
)