package com.carleodev.helados.data


import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descrip: String,
    val price: Double,
    val preciobs: Double,
    val cantidad: Int,
    val estatus:Int,
    val imagen:Bitmap?
)

