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
import androidx.compose.material.icons.filled.Print
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
                onClick = { viewModel.guardarTicket().
                also { if (viewModel.ticketUIState.pago.isNotBlank()) onNavigateUp()  } },
                modifier = Modifier.navigationBarsPadding()
            )
            {
                Icon(
                    imageVector = Icons.Default.Print,
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

                        OutlinedTextField(
                            value = ticketUIState.cant,
                            label = { Text("CANTIDAD") },
                            modifier = Modifier
                                .width(100.dp),
                            onValueChange = { onValueChange(ticketUIState.copy(cant = it)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }),
                        )

                        Button(
                            onClick = { ticketUIState.suma() },
                            modifier = Modifier.width(60.dp).height(60.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "+",
                                tint = MaterialTheme.colors.onPrimary
                            )

                        }
                    }
                    if (ticketUIState.formapago) {
                        Column {
                            Text(
                                "SABOR -> ${ticketUIState.sabor}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                "TOPING -> ${ticketUIState.toping}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                "LLUVIA -> ${ticketUIState.lluvia}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                        }
                    }
                   
                }

            }
            val tamanoTexto = 20.sp
            if (!ticketUIState.formapago) {
                Column() {
                    Text(
                        "SABORES -> ${ticketUIState.sabor}",
                        fontWeight = FontWeight.Bold,
                        fontSize = tamanoTexto
                    )
                    sabores(ticketUIState, onValueChange)
                    Text(
                        "TOPING ",
                        fontWeight = FontWeight.Bold,
                        fontSize = tamanoTexto
                    )
                    toping(ticketUIState, onValueChange)
                    Text(
                        "LLUVIA ",
                        fontWeight = FontWeight.Bold,
                        fontSize = tamanoTexto
                    )
                    lluvia(ticketUIState, onValueChange)

                }
            } else {
                pagos(ticketUIState = ticketUIState, onValueChange = onValueChange)
            }


        }
    }

}

@Composable
fun sabores( ticketUIState: TicketUIState,
             onValueChange:(TicketUIState)->Unit) {

    val borderWidth = 2.dp
    val modifierSelected = Modifier
        .size(100.dp)
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


    //var toping:MutableSet<String> by remember { mutableStateOf(mutableSetOf()) }

    val borderWidth = 2.dp

    val colModfier = Modifier
        .padding(5.dp)
        .border(
            BorderStroke(borderWidth, color = Color.White)
        )

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

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)
           ) {
        Column(modifier=colModfier) {
            Switch(
                checked = fresaChecked,
                onCheckedChange = {
                    fresaChecked = it

                },
            )
            if (fresaChecked) {
                Image(
                    painter = painterResource(R.drawable.siropefresa),
                    contentDescription = null, modifier = modifierSelected
                )
                ticketUIState.agregarToping("Fresa")
            } else {
                Image(
                    painter = painterResource(R.drawable.siropefresa),
                    contentDescription = null, modifier = modifierUnSelected
                )
                ticketUIState.borrarToping("Fresa")
            }

        }
        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier=colModfier) {
            Switch(
                checked = chocoChecked,
                onCheckedChange = {
                    chocoChecked = it

                },
            )
            if (chocoChecked) {
                Image(
                    painter = painterResource(R.drawable.siropechocolate),
                    contentDescription = null, modifier = modifierSelected
                )
                ticketUIState.agregarToping("Chocolate")
            } else {
                Image(
                    painter = painterResource(R.drawable.siropechocolate),
                    contentDescription = null, modifier = modifierUnSelected
                )
                ticketUIState.borrarToping("Chocolate")
            }

        }
        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier=colModfier) {
            Switch(
                checked = lecheChecked,
                onCheckedChange = {
                    lecheChecked = it

                },
            )
            if (lecheChecked) {
                Image(
                    painter = painterResource(R.drawable.siropeleche),
                    contentDescription = null, modifier = modifierSelected
                )
                ticketUIState.agregarToping("Leche Condensada")
            } else {
                Image(
                    painter = painterResource(R.drawable.siropeleche),
                    contentDescription = null, modifier = modifierUnSelected
                )
                ticketUIState.borrarToping("Leche Condensada")
            }

        }
    }


    }



@Composable
fun lluvia( ticketUIState: TicketUIState,
            onValueChange:(TicketUIState)->Unit) {

    var maniChecked by remember { mutableStateOf(false) }
    var chocoChecked by remember { mutableStateOf(false) }
    var coloresChecked by remember { mutableStateOf(false) }
    var oreoChecked by remember { mutableStateOf(false) }


    var lluvia:MutableSet<String> by remember { mutableStateOf(mutableSetOf()) }

    val borderWidth = 2.dp
    val spacio = Modifier.size(10.dp)

    val colModfier = Modifier
        .padding(5.dp)
        .border(
            BorderStroke(borderWidth, color = Color.White)
        )

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

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(modifier=colModfier) {
            Switch(
                checked = maniChecked,
                onCheckedChange = {
                    maniChecked = it

                },
            )
            if (maniChecked) {
                lluvia.add("Mani")
                Image(
                    painter = painterResource(R.drawable.lluviamani),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(lluvia = lluvia))
            } else {
                lluvia.remove("Mani")
                Image(
                    painter = painterResource(R.drawable.lluviamani),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(lluvia = lluvia))
            }

        }
        Spacer(modifier = spacio)
        Column(modifier=colModfier) {
            Switch(
                checked = chocoChecked,
                onCheckedChange = {
                    chocoChecked = it

                },
            )
            if (chocoChecked) {
                lluvia.add("Chocolate")
                Image(
                    painter = painterResource(R.drawable.lluviachoco),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            } else {
                lluvia.remove("Chocolate")
                Image(
                    painter = painterResource(R.drawable.lluviachoco),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            }

        }
        Spacer(modifier = spacio)
        Column(modifier=colModfier) {
            Switch(
                checked = coloresChecked,
                onCheckedChange = {
                    coloresChecked = it
                },
            )
            if (coloresChecked) {
                lluvia.add("Colores")
                Image(
                    painter = painterResource(R.drawable.lluviacolores),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            } else {
                lluvia.remove("Colores")
                Image(
                    painter = painterResource(R.drawable.lluviacolores),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            }

        }
        Spacer(modifier = spacio)
        Column(modifier=colModfier) {
            Switch(
                checked = oreoChecked,
                onCheckedChange = {
                    oreoChecked= it
                },
            )
            if (oreoChecked) {
                lluvia.add("Oreo")
                Image(
                    painter = painterResource(R.drawable.lluviaoreo),
                    contentDescription = null, modifier = modifierSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            } else {
                lluvia.remove("Oreo")
                Image(
                    painter = painterResource(R.drawable.lluviaoreo),
                    contentDescription = null, modifier = modifierUnSelected
                )
                onValueChange(ticketUIState.copy(lluvia=lluvia))
            }

        }
    }


}

@Composable
fun pagos( ticketUIState: TicketUIState,
             onValueChange:(TicketUIState)->Unit) {

    val borderWidth = 2.dp
    val modifierSelected = Modifier
        .size(100.dp)
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
            onClick = { onValueChange(ticketUIState.copy(pago="Efectivo Bs")) },
            content = {
                if (ticketUIState.pago == "Efectivo Bs") {
                    Column() {
                        Image(painter =painterResource(R.drawable.pagobs),
                            contentDescription = null, modifier = modifierSelected)
                    }

                } else {
                    Image(painter = painterResource(R.drawable.pagobs),
                        contentDescription = null, modifier = modifierUnSelected)
                }
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(pago="Efectivo Dolar")) },
            content = {
                if (ticketUIState.pago == "Efectivo Dolar") {
                    Column() {
                          Image(
                            painter = painterResource(R.drawable.pagodolar),
                            contentDescription = null, modifier = modifierSelected
                        )
                    }
                } else {
                    Image(painter = painterResource(R.drawable.pagodolar),
                        contentDescription = null, modifier = modifierUnSelected)
                }
            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(pago="Tarjeta")) },
            content = {
                if (ticketUIState.pago == "Tarjeta") {
                    Column() {
                        Image(
                            painter = painterResource(R.drawable.tarjeta),
                            contentDescription = null, modifier = modifierSelected
                        )

                    }
                } else {
                    Image(painter = painterResource(R.drawable.tarjeta),
                        contentDescription = null, modifier = modifierUnSelected)
                }


            }
        )

        OutlinedButton(
            onClick = { onValueChange(ticketUIState.copy(pago="Pago Movil")) },
            content = {
                if (ticketUIState.pago == "Pago Movil") {
                    Column() {
                        Image(
                            painter = painterResource(R.drawable.pagomovil),
                            contentDescription = null, modifier = modifierSelected
                        )

                    }
                } else {
                    Image(painter = painterResource(R.drawable.pagomovil),
                        contentDescription = null, modifier = modifierUnSelected)
                }


            }
        )
    }
}

