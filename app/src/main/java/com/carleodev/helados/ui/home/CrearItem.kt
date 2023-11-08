package com.carleodev.helados.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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
            onNavigateUp=onNavigateUp,
            modifier = modifier.padding(innerPadding))

    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormularioItem(itemUIState:ItemUIState,
                   onValueChange:(ItemUIState)->Unit,
                   onGuardar:()->Unit,
                   onNavigateUp: () -> Unit,
                   modifier: Modifier = Modifier) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var myBitmap by remember { mutableStateOf<Bitmap?>(null) }


    /*val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        Log.d("testimg",uri.toString())
        myBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

    }**/

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        myBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

    }
   /* val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    var bitmap:Bitmap

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                capturedImageUri = uri
                //bitmap = convertToBitmap(uri =uri, context= context,50,50)
                onValueChange(itemUIState.copy(imagen = capturedImageUri.toString()))

            }
        }*/


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
                    onValueChange = { onValueChange(itemUIState.copy(descrip = it.uppercase())) },
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
                launcher.launch("image/*")
                itemUIState.copy(imagen = myBitmap)
            }

            ) {
                Text("SELECCIONAR IMAGEN")
            }
            Button(onClick = {
                onValueChange(itemUIState.copy(imagen = myBitmap))
                onGuardar()
                //onNavigateUp()
            }
            ) {
                Text("GUARDAR")
            }

            displayImage(myBitmap)

            Spacer(modifier = Modifier.height(12.dp))


        }
    }

}



@Composable
fun displayImage(bitmap:Bitmap?) {

    //val uri = Uri.parse(stfrinUri)

    Column(

        Modifier
            .width(50.dp)
            .height(50.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Image(
            painter = rememberImagePainter(bitmap),
            contentDescription = "Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(1.dp)
        )
    }
}





