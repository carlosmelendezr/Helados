package com.carleodev.helados.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.room.TypeConverter
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.request.ImageRequest
import java.io.ByteArrayOutputStream
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


}

suspend fun convertToBitmap(uri: Uri, context: Context, widthPixels: Int, heightPixels: Int): Bitmap {
    val mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(mutableBitmap)
    val drawable = getDrawable(uri, context)
    drawable?.setBounds(0, 0, widthPixels, heightPixels)
    drawable?.draw(canvas)
    return mutableBitmap
}


@OptIn(ExperimentalCoilApi::class)
suspend fun getDrawable(uri: Uri, context: Context): Drawable? {
    val imageLoader = ImageLoader.Builder(context)
        .allowHardware(false)
        .crossfade(true)
        .build()

    val request= ImageRequest.Builder(context)
        .data(uri)
        .build()
    return imageLoader.execute(request).drawable
}