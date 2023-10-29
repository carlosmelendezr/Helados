package com.carleodev.helados.data

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Entity(tableName = "tasa")
data class Tasa (
    @PrimaryKey(autoGenerate = false)
    @Expose(serialize = false,deserialize = true)
    val id:Int = 0,
    val dolar:Double=0.0,
)

fun InicializarTasa(tasaRepository: TasaRepository,
                    valor:Double) {
    val TASA_DOLAR = 1

    runBlocking {
        val existe = tasaRepository.getItemStream(TASA_DOLAR).first()

        if (existe != null) {
            tasaRepository.updateItem(Tasa(id = 1, dolar = valor))
        } else {
            tasaRepository.insertItem(Tasa(id = 1, dolar = valor))
        }
    }
}