package com.carleodev.helados.ui.home

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
    navigateToDetalles:()->Unit,
    viewModel: ReporteDiaViewModel = viewModel(factory = AppViewModelProvider.Factory))
{

    Scaffold(
        topBar = {
            BotesTopAppBar(
                title = "Reporte del dia " ,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToDetalles() },
                modifier = Modifier.navigationBarsPadding()
            )
            {
                Icon(
                    imageVector = Icons.Default.Airlines,
                    contentDescription = "Detalles",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
    ) { innerPadding ->
        MostarPagos(listaUiState.itemList,
            boteAzul =boteAzul+boteAzulExp,
            boteAmarillo = boteAmarillo+boteAmarilloExp,
            botesAnulados = botesAnulados,
            modifier = modifier.padding(innerPadding))

    }
}

@Composable
fun MostarPagos(lista:List<Pagos>,
                boteAzul:Int, boteAmarillo:Int,
                botesAnulados:Int,
                modifier: Modifier = Modifier)
{
    Card() {
        Column()
        {
            Text(
                text = "RESUMEN DE VENTA",
                style = MaterialTheme.typography.h6,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "TOTAL BOTES AZULES : ${boteAzul}",
                style = MaterialTheme.typography.h6,
                fontSize = 12.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "TOTAL BOTES AMARILLOS : ${boteAmarillo}",
                style = MaterialTheme.typography.h6,
                fontSize = 12.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "TOTAL BOTES ANULADOS : ${botesAnulados}",
                style = MaterialTheme.typography.h6,
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                content = {
                    items(items = lista) {
                        ItemPago(it)
                    }
                }

            )
        }

    }
}

@Composable
fun ItemPago(pagos:Pagos) {


    Row() {
        when (pagos.tipopago) {
            1 -> Text("DIVISA")
            2 -> Text("PV Venezuela")
            3 -> Text("PV BancaAmiga")
            4 -> Text("EFECTIVO BS")
            5 -> Text("PAGO MOVIL")
        }
        Text("${pagos.monto}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right)
    }


}


@Composable
@Preview
fun  MostarPagosPreview(){
    MostarPagos(listOf(Pagos(idticket = 1, tipopago = 1,monto = 600.00,fecha=20230730),
        Pagos(idticket = 1, tipopago = 2,monto = 5500.00, fecha=20230730)), boteAzul = 8,
        boteAmarillo = 3, botesAnulados = 2)

}