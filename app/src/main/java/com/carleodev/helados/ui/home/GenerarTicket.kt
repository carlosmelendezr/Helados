package com.carleodev.helados.ui.home

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.ui.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carleodev.helados.AppViewModelProvider
import com.carleodev.helados.HeladosTopAppBar
import com.carleodev.helados.R
import com.carleodev.helados.navigation.NavigationDestination
import com.carleodev.helados.viewmodels.CrearItemViewModel
import com.carleodev.helados.viewmodels.GenerarTicketViewModel
import com.carleodev.helados.viewmodels.ItemUIState
import com.carleodev.helados.viewmodels.TicketUIState
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter


object GenerarTicketDestination : NavigationDestination {
    override val route = "genrarticket"
    override val titleRes = 3
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}


@Composable
fun GenerarTicketScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenerarTicketViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{

    Scaffold(
        topBar = {
            HeladosTopAppBar(
                title = "Generar Ticket" ,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.guardarTicket().also { onNavigateUp()  } },
                modifier = Modifier.navigationBarsPadding()
            )
            {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Imp",
                    tint = MaterialTheme.colors.onPrimary
                )

            }
        },
    ) { innerPadding ->

        GenerarItem(itemUIState = viewModel.itemUIState,
            ticketUIState=viewModel.ticketUIState,
            onValueChange=viewModel::updateUiState,
            modifier = modifier.padding(innerPadding))

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GenerarItem(itemUIState: ItemUIState,
                ticketUIState: TicketUIState,
                onValueChange:(TicketUIState)->Unit,
                modifier: Modifier = Modifier
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val myBitmap = itemUIState.imagen



    Card(

        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 10.dp)

    {
        Column() {

            Row {
                displayImage(myBitmap)
                Spacer(modifier = Modifier.height(5.dp))
                Column {
                    Text(
                        itemUIState.descrip,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,

                        )
                    Row() {
                     Text(
                        "${itemUIState.price}$ ---> ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp

                        )

                        Text(
                            "${itemUIState.preciobs} BS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp

                        )



                    }
                    Row() {
                        sabores(ticketUIState,onValueChange)
                        //toping(ticketUIState,onValueChange)
                        //lluvia(ticketUIState,onValueChange)

                    }
                }
            }


        }
    }

}

@Composable
fun sabores( ticketUIState: TicketUIState,
             onValueChange:(TicketUIState)->Unit) {

    Column(modifier = Modifier.
        border(BorderStroke(2.dp, SolidColor(Color.Blue)))
        .padding(10.dp)) {


        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Ron Pasas")) },
            content = {
                if (ticketUIState.sabor == "Ron Pasas") {
                    Image(painter =painterResource(R.drawable.selronpasas), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.ronpasas), contentDescription = null)
                }
                Text("RON PASAS  ")
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mantecado")) },
            content = {
                if (ticketUIState.sabor == "Mantecado") {
                    Image(painter =painterResource(R.drawable.mantecadosel), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.mantecado), contentDescription = null)
                }
                Text("MANTECADO")

            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mixto")) },
            content = {
                if (ticketUIState.sabor == "Mixto") {
                    Image(painter =painterResource(R.drawable.selmixto), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.mixto), contentDescription = null)
                }
                Text("MIXTO         ")

            }
        )






    }
}



@Composable
fun toping(ticketUIState: TicketUIState,
           onValueChange:(TicketUIState)->Unit) {

    Column(modifier = Modifier.
    border(BorderStroke(2.dp, SolidColor(Color.Blue)))
        .padding(10.dp)) {

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Fresa")) },
            content = {
                if (ticketUIState.sabor == "Fresa") {
                    Image(painter =painterResource(R.drawable), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.ronpasas), contentDescription = null)
                }
                Text("RON PASAS  ")
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mantecado")) },
            content = {
                if (ticketUIState.sabor == "Mantecado") {
                    Image(painter =painterResource(R.drawable.mantecadosel), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.mantecado), contentDescription = null)
                }
                Text("MANTECADO")

            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mixto")) },
            content = {
                if (ticketUIState.sabor == "Mixto") {
                    Image(painter =painterResource(R.drawable.selmixto), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.mixto), contentDescription = null)
                }
                Text("MIXTO         ")

            }
        )






    }

}

@Composable
fun lluvia(ticketUIState: TicketUIState,
           onValueChange:(TicketUIState)->Unit) {
    val selectedOption = remember { mutableStateOf("Chocolate") }

    Column(modifier = Modifier.border(BorderStroke(2.dp, SolidColor(Color.Black))).padding(10.dp)) {

        RadioButton(
            selected = ticketUIState.lluvia== "Chocolate",
            onClick = { onValueChange(ticketUIState.copy(lluvia="Chocolate"))  },

            )
        Text("Chocolate")

        RadioButton(
            selected = ticketUIState.lluvia == "Colores",
            onClick = { onValueChange(ticketUIState.copy(lluvia="Colores"))  },

            )
        Text("Colores")

        RadioButton(
            selected = ticketUIState.lluvia == "Mani",
            onClick = { onValueChange(ticketUIState.copy(lluvia="Mani"))  },
            )
        Text("Mani")

        RadioButton(
            selected = ticketUIState.lluvia == "Oreo",
            onClick = { onValueChange(ticketUIState.copy(lluvia="Oreo"))  },
        )
        Text("Oreo")



    }
}
