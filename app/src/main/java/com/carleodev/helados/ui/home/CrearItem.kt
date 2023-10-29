package com.carleodev.helados.ui.home

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carleodev.helados.AppViewModelProvider
import com.carleodev.helados.viewmodels.CrearItemViewModel
import com.carleodev.helados.HeladosTopAppBar
import com.carleodev.helados.navigation.NavigationDestination



object CrearItemDestination : NavigationDestination {
    override val route = "crearitem"
    override val titleRes = 1
}


@Composable
fun CrearItemScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CrearItemViewModel = viewModel(factory = AppViewModelProvider.Factory))
{

    Scaffold(
        topBar = {
            HeladosTopAppBar(
                title = "Crear Item" ,
                canNavigateBack = false,
                navigateUp = onNavigateUp
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                modifier = Modifier.navigationBarsPadding()
            )
            {

            }
        },
    ) { innerPadding ->

        FormularioItem(modifier = modifier.padding(innerPadding))

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormularioItem(modifier: Modifier = Modifier) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val colorFondo =  Color.Blue



    Card(
        backgroundColor = colorFondo,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp)

    {
        Column(

        ) {

            // Encabezado
            Text(
                text = "CREAR NUEVO ITEM",
                style = MaterialTheme.typography.h6,
                fontSize = 42.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            // Subtítulo
            /*Row {
                OutlinedTextField(
                    value = horaUiState.cant,
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.Green),
                    onValueChange = { onCantChange(horaUiState.copy(cant = it)) },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }),

                    )

                Text(
                    text = "Total Pagos ${totalPagos}",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "FORMA DE PAGO",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            FormadePago(pagoUIState, onPagoChange)
            Spacer(modifier = Modifier.height(12.dp))
            //ListarPagos(listaPago)

            Spacer(modifier = Modifier.height(24.dp))*/
        }
    }

}

