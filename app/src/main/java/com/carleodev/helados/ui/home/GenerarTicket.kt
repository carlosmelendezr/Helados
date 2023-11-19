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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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

                }

            }
            Column() {
                Text(
                    "SABORES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                sabores(ticketUIState,onValueChange)
                Text(
                    "TOPING",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                toping(ticketUIState,onValueChange)
                Text(
                    "LLUVIA",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                //lluvia(ticketUIState,onValueChange)

            }


        }
    }

}

@Composable
fun sabores( ticketUIState: TicketUIState,
             onValueChange:(TicketUIState)->Unit) {

    val borderWidth = 2.dp
    val modifierSelected = Modifier
        .size(80.dp)
        .border(
            BorderStroke(borderWidth, Color.Red),
            CircleShape
        )
        .padding(borderWidth)
        .clip(CircleShape)

    val modifierUnSelected = Modifier
        .size(100.dp)



    Row() {
        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Ron Pasas")) },
            content = {
                if (ticketUIState.sabor == "Ron Pasas") {
                    Column() {
                        Text("RON PASAS")
                        Image(painter =painterResource(R.drawable.ronpasas),
                            contentDescription = null, modifier = modifierSelected)
                    }

                } else {
                    Image(painter = painterResource(R.drawable.ronpasas),
                        contentDescription = null, modifier = modifierUnSelected)
                }
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mantecado")) },
            content = {
                if (ticketUIState.sabor == "Mantecado") {
                    Column() {
                        Text("MANTECADO")
                        Image(
                            painter = painterResource(R.drawable.mantecado),
                            contentDescription = null, modifier = modifierSelected
                        )

                    }
                } else {
                    Image(painter = painterResource(R.drawable.mantecado),
                        contentDescription = null, modifier = modifierUnSelected)
                }


            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Mixto")) },
            content = {
                if (ticketUIState.sabor == "Mixto") {
                    Column() {
                        Text("MIXTO")
                        Image(
                            painter = painterResource(R.drawable.mixto),
                            contentDescription = null, modifier = modifierSelected
                        )

                    }
                } else {
                    Image(painter = painterResource(R.drawable.mixto),
                        contentDescription = null, modifier = modifierUnSelected)
                }


            }
        )
    }
}

@Composable
fun toping( ticketUIState: TicketUIState,
             onValueChange:(TicketUIState)->Unit) {
    var fresaChecked by remember { mutableStateOf(false) }
    var chocoChecked by remember { mutableStateOf(false) }
    var lecheChecked by remember { mutableStateOf(false) }

    var toping:MutableSet<String> by remember { mutableStateOf(mutableSetOf()) }

    val borderWidth = 2.dp
    val modifierSelected = Modifier
        .size(80.dp)
        .border(
            BorderStroke(borderWidth, Color.Red),
            CircleShape
        )
        .padding(borderWidth)
        .clip(CircleShape)

    val modifierUnSelected = Modifier
        .size(80.dp)

    Row(
        horizontalArrangement = Arrangement.spacedBy(50.dp)
           ) {
        Column() {
            Switch(
                checked = fresaChecked,
                onCheckedChange = {
                    fresaChecked = it

                },
            )
            if (fresaChecked) {
                toping.add("Fresa")
                Image(
                    painter = painterResource(R.drawable.siropefresa),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            } else {
                toping.remove("Fresa")
                Image(
                    painter = painterResource(R.drawable.siropefresa),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            }

        }

        Column() {
            Switch(
                checked = chocoChecked,
                onCheckedChange = {
                    chocoChecked = it

                },
            )
            if (chocoChecked) {
                toping.add("Chocolate")
                Image(
                    painter = painterResource(R.drawable.siropechocolate),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            } else {
                toping.remove("Chocolate")
                Image(
                    painter = painterResource(R.drawable.siropechocolate),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            }

        }
        Column() {
            Switch(
                checked = lecheChecked,
                onCheckedChange = {
                    lecheChecked = it

                },
            )
            if (lecheChecked) {
                toping.add("Leche Condensada")
                Image(
                    painter = painterResource(R.drawable.siropeleche),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            } else {
                toping.remove("Leche Condensada")
                Image(
                    painter = painterResource(R.drawable.siropeleche),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(toping = toping))
            }

        }
    }


    }




/*@Composable
fun toping(ticketUIState: TicketUIState,
           onValueChange:(TicketUIState)->Unit) {

    Column(modifier = Modifier.
    border(BorderStroke(2.dp, SolidColor(Color.Blue)))
        .padding(10.dp)) {

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Fresa")) },
            content = {
                if (ticketUIState.sabor == "Fresa") {
                    Image(painter =painterResource(R.drawable.siropefresasel), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.siropefresa), contentDescription = null)
                }
                Text("FRESA   ")
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Chocolate")) },
            content = {
                if (ticketUIState.sabor == "Chocolate") {
                    Image(painter =painterResource(R.drawable.siropechocolatesel), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.siropechocolate), contentDescription = null)
                }
                Text("CHOCOLATE")

            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(sabor="Leche")) },
            content = {
                if (ticketUIState.sabor == "Leche") {
                    Image(painter =painterResource(R.drawable.siropelechesel), contentDescription = null)
                } else {
                    Image(painter = painterResource(R.drawable.siropeleche), contentDescription = null)
                }
                Text("LECHE CONDENSADA")

            }
        )






    }

}*/

@Composable
fun lluvia(ticketUIState: TicketUIState,
           onValueChange:(TicketUIState)->Unit) {
    val selectedOption = remember { mutableStateOf("Chocolate") }

    Column(modifier = Modifier
        .border(BorderStroke(2.dp, SolidColor(Color.Black)))
        .padding(10.dp)) {

        RadioButton(
            selected = ticketUIState.lluvia == "Chocolate",
            onClick = { onValueChange(ticketUIState.copy(lluvia = "Chocolate")) },

            )
        Text("Chocolate")

        RadioButton(
            selected = ticketUIState.lluvia == "Colores",
            onClick = { onValueChange(ticketUIState.copy(lluvia = "Colores")) },

            )
        Text("Colores")

        RadioButton(
            selected = ticketUIState.lluvia == "Mani",
            onClick = { onValueChange(ticketUIState.copy(lluvia = "Mani")) },
        )
        Text("Mani")

        RadioButton(
            selected = ticketUIState.lluvia == "Oreo",
            onClick = { onValueChange(ticketUIState.copy(lluvia = "Oreo")) },
        )
        Text("Oreo")


    }

}
