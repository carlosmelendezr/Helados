package com.carleodev.helados.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airlines

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carleodev.helados.AppViewModelProvider
import com.carleodev.helados.HeladosTopAppBar
import com.carleodev.helados.data.Ticket
import com.carleodev.helados.navigation.NavigationDestination
import com.carleodev.helados.viewmodels.ReporteDiaViewModel


object ReporteDiaDestination : NavigationDestination {
    override val route = "reportedia"
    override val titleRes = 4
}


@Composable
fun ReporteDiaScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReporteDiaViewModel = viewModel(factory = AppViewModelProvider.Factory))
{
    val totalCantidad by viewModel.totalCant.collectAsState(initial = 0)
    //val totalDolar by viewModel.totalDolar.collectAsState(initial = 0.0)
    //val totalBs by viewModel.totalBs.collectAsState(initial = 0.0)
    val totalDolarEfectivo by viewModel.totalDolarEfectivo.collectAsState()
    val totalBsEfectivo by viewModel.totalBsEfectivo.collectAsState()
    val totalTarjeta by viewModel.totalTarjeta.collectAsState()
    val totalPagoMovil by viewModel.totalPagoMovil.collectAsState()


    Scaffold(
        topBar = {
            HeladosTopAppBar(
                title = "Reporte del dia " ,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }

    ) { innerPadding ->
        MostarReporte(totalCantidad=totalCantidad,
            totalDolarEfectivo=totalDolarEfectivo,
            totalBsEfectivo=totalBsEfectivo,
            totalTarjeta=totalTarjeta,
            totalPagoMovil=totalPagoMovil,
            modifier = modifier.padding(innerPadding))

    }
}

@Composable
fun MostarReporte(totalCantidad:Int,
                  totalDolarEfectivo:Double,
                  totalBsEfectivo:Double,
                  totalTarjeta:Double,
                  totalPagoMovil:Double,
                  modifier: Modifier = Modifier)
{
    Card() {
        val fontSize = 20.sp
        Column()
        {
            Text(
                text = "RESUMEN DE VENTA",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "TOTAL TICKETS : ${totalCantidad}",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )



            Text(
                text = "PAGOS BOLIVARES EFECTIVO : ${totalBsEfectivo}",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "PAGOS EFECTIVO DOLAR : ${totalDolarEfectivo}$",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "PAGOS TARJETA : ${totalTarjeta}$",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "PAGOS PAGO MOVIL : ${totalPagoMovil}$",
                style = MaterialTheme.typography.h6,
                fontSize = fontSize,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )


        }

    }
}



@Composable
@Preview
fun  MostarPagosPreview(){
    /*MostarPagos(listOf(Pagos(idticket = 1, tipopago = 1,monto = 600.00,fecha=20230730),
        Pagos(idticket = 1, tipopago = 2,monto = 5500.00, fecha=20230730)), boteAzul = 8,
        boteAmarillo = 3, botesAnulados = 2)*/

}