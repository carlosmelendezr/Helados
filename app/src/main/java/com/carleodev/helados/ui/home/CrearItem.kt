package com.carleodev.helados.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.carleodev.helados.AppViewModelProvider
import com.carleodev.helados.HeladosTopAppBar
import com.carleodev.helados.navigation.NavigationDestination
import com.carleodev.helados.viewmodels.CrearItemViewModel
import com.carleodev.helados.viewmodels.ItemUIState
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


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
                title = "Crear/Modificar Item" ,
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

        FormularioItem(itemUIState = viewModel.itemUIState,
            onValueChange=viewModel::updateUiState,
            onGuardar=viewModel::guardar,
            modifier = modifier.padding(innerPadding))

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormularioItem(itemUIState:ItemUIState,
                   onValueChange:(ItemUIState)->Unit,
                   onGuardar:()->Unit,
                   modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    val result = remember { mutableStateOf<String?>("") }

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                capturedImageUri = it
            }
        }


    Card(

        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp)

    {
        Column() {

            Text(
                text = "CREAR/MODIFICAR ITEM",
                style = MaterialTheme.typography.h6,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row {
                // Encabezado


                OutlinedTextField(
                    value = itemUIState.descrip,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = { onValueChange(itemUIState.copy(descrip = it)) },
                    label = { Text("Descripcion") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }),

                    )
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = itemUIState.price,
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Precio $") },
                onValueChange = { onValueChange(itemUIState.copy(price = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }),
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                launcher.launch(
                    PickVisualMediaRequest(
                      mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
                ).also { onValueChange(itemUIState.copy(imagen = capturedImageUri.toString())) }
            }
            ) {
                Text("SELECCIONAR IMAGEN {${capturedImageUri.path} }")
            }
            displayImage(capturedImageUri)

            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                onGuardar()
            }
            ) {
                Text("GUARDAR")
            }


        }
    }

}

@Composable
fun displayImage(uri:Uri) {

    Column(

        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        // on below line we are adding
        // horizontal alignment for our column
        horizontalAlignment = Alignment.CenterHorizontally,
        // on below line we are adding
        // vertical arrangement for our column
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            // on below line we are specifying text to display.
            text = "Image from File Path in Android",

            // on below line we are specifying
            // modifier to fill max width.
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            // on below line we are
            // specifying text alignment.
            textAlign = TextAlign.Center,

            // on below line we are
            // specifying color for our text.
            color = Color.Black,

            // on below line we are
            // specifying font weight
            fontWeight = FontWeight.Bold,

            // on below line we
            // are updating font size.
            fontSize = 25.sp,
        )

        //val imgFile = File("/storage/emulated/0/Pictures/Helado.jpg")


        // on below line we are checking if the image file exist or not.
        /*var imgBitmap: Bitmap? = null
        if (imgFile.exists()) {
            // on below line we are creating an image bitmap variable
            // and adding a bitmap to it from image file.
            imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        }*/

        Image(
            //painter = rememberAsyncImagePainter(model = imgBitmap),
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
        )
    }
}



