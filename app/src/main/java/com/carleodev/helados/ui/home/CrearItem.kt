package com.carleodev.helados.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
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
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
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
                canNavigateBack = true,
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
    myBitmap = itemUIState.imagen


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        myBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

    }

    Card(

        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 10.dp)

    {
        Column() {

            Row {
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
            Spacer(modifier = Modifier.height(2.dp))
            Row () {
                OutlinedTextField(
                    value = itemUIState.price,
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
                Spacer(modifier = Modifier.height(2.dp))
                OutlinedTextField(
                    value = itemUIState.preciobs,
                    label = { Text("Precio Bs") },
                    onValueChange = { onValueChange(itemUIState.copy(preciobs = it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }),
                )
            }
            Spacer(modifier = Modifier.height(2.dp))

            Button(onClick = {
                launcher.launch("image/*").also {
                    if ( myBitmap!=null) {
                        itemUIState.copy(imagen = myBitmap)
                    }
                }
            }

            ) {
                Text("SELECCIONAR IMAGEN")
            }


            displayImage(myBitmap)
            Button(onClick = {
                if ( myBitmap!=null) {
                    onValueChange(itemUIState.copy(imagen = myBitmap))
                }
                //
                onGuardar()
                onNavigateUp()
            },
            ) {
                Text("GUARDAR")
            }




        }
    }

}



@Composable
fun displayImage(bitmap:Bitmap?) {

    Column(

        Modifier
            .width(150.dp)
            .height(150.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Image(
            painter = rememberImagePainter(bitmap),
            contentDescription = "Image",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .padding(1.dp)
        )
    }
}





