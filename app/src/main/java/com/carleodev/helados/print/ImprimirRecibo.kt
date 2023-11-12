package com.carleodev.helados.print

import android.R
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.carleodev.helados.data.Item
import com.carleodev.helados.data.Ticket
import com.carleodev.helados.util.convertIntToTimeScreen
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.ImagePrintable
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.utilities.Printing

import java.util.*
import kotlin.collections.ArrayList


fun ImprimirRecibo(context:Context, ticket: Ticket,  item: Item) {
    var printing : Printing? = null

    Printooth.setPrinter("DP80UBT-16", "66:32:A4:66:0A:D0")

    if (Printooth.hasPairedPrinter())
        printing = Printooth.printer() else    Log.d("BPRINT","Impresora No conectada....")

    val printables = getSomePrintables(context ,ticket, item)

    printing?.print(printables)


}

private fun finalizar(context:Context ) = ArrayList<Printable>().apply {
    add(
        TextPrintable.Builder()
            .setText("-------------------------------")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_30)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
            .setNewLinesAfter(1)
            .build())

    add(RawPrintable.Builder(byteArrayOf(27, 86, 66, 0)).build())
    add(RawPrintable.Builder(byteArrayOf(27, 86, 66, 1)).build())
}


private fun getSomePrintables(context:Context,
                              ticket: Ticket, item: Item
) = ArrayList<Printable>().apply {


    //add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode


    val LogoHelado: Bitmap = BitmapFactory.decodeResource(context.resources,
        com.carleodev.helados.R.drawable.helado)

    //logo
    add(ImagePrintable.Builder(
        LogoHelado)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .build())

    add(
        TextPrintable.Builder()
            .setText("CAFE RIBIERA BRAVA 2020")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_30)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
            .setNewLinesAfter(1)
            .build())


    add(
        TextPrintable.Builder()
            .setText("HELADERIA")
            .setLineSpacing(DefaultPrinter.LINE_SPACING_30)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_OFF)
            .setNewLinesAfter(1)
            .build())




    add(
        TextPrintable.Builder()
            .setText("TICKET NO: ${ticket.id}" )
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
            .setNewLinesAfter(1)
            .build())

    add(
        TextPrintable.Builder()
            .setText("FECHA:${convertIntToTimeScreen(ticket.fecha)}")
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(2)
            .build())

    add(
        TextPrintable.Builder()
            .setText("------------------------")
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(2)
            .build())

    add(TextPrintable.Builder()
        .setText("${item.descrip}")
        .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
        .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
        .setNewLinesAfter(1)
        .build())

    add(TextPrintable.Builder()
        .setText("${item.preciobs.toInt()} Bs.")
        .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
        .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
        .setNewLinesAfter(2)
        .build())

    add(TextPrintable.Builder()
        .setText("Sabor: ${ticket.sabor}")
        .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
        .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
        .setNewLinesAfter(1)
        .build())

    add(TextPrintable.Builder()
        .setText("Topping: ${ticket.toping}")
        .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
        .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
        .setNewLinesAfter(1)
        .build())

    add(TextPrintable.Builder()
        .setText("LLuvia: ${ticket.lluvia}")
        .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
        .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
        .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
        .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
        .setNewLinesAfter(1)
        .build())



    add(
        TextPrintable.Builder()
            .setText("------------------------")
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(3)
            .build())


    /*add(
        TextPrintable.Builder()

            .setText("${ticket.}")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())

    add(
        TextPrintable.Builder()

            .setText("CANT. BOTES : ${tickets.cant}")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setFontSize(DefaultPrinter.FONT_SIZE_LARGE)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())*/





    // Cortar el papel
    add(RawPrintable.Builder(byteArrayOf(29, 86, 66, 10)).build())



}