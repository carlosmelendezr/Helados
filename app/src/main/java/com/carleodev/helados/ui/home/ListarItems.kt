package com.carleodev.helados.ui.home


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.carleodev.helados.R
import com.carleodev.helados.data.Item
import com.carleodev.helados.navigation.NavigationDestination
import com.carleodev.helados.viewmodels.ItemUIState
import com.carleodev.helados.viewmodels.ListarItemViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


object ListarItemDestination : NavigationDestination {
    override val route = "listaritem"
    override val titleRes = 2
}


@Composable
fun ListarItemScreen(
    onNavigateUp: () -> Unit,
    navigateToEditItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListarItemViewModel = viewModel(factory = AppViewModelProvider.Factory))
{
    val listaUiState by viewModel.listaUiState.collectAsState()

    Scaffold(
        topBar = {
            HeladosTopAppBar(
                title = "Listado de Items" ,
                canNavigateBack = false,
                navigateUp = onNavigateUp
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(0) },
                modifier = Modifier.navigationBarsPadding()
            )
            {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear Item",
                    tint = MaterialTheme.colors.onPrimary
                )

            }
        },
    ) { innerPadding ->

        ListaItems(itemList= listaUiState.listas,
            onItemClick = navigateToEditItem,
            onDelete = viewModel::deleteLista,
            modifier = modifier.padding(innerPadding))

    }
}

@Composable
private fun ListaItems(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: (Item) -> Unit,
) {


    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(3),
        content = {
            items(itemList.size) { index ->
                ListaItemRow(
                    item = itemList[index],
                    onItemClick = onItemClick, onDelete = onDelete
                )
            }
        }
    )

    /*LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        items(items = itemList, key = { it.id }) { listaitem ->
            ListaItemRow(item = listaitem,
                onItemClick = onItemClick,onDelete=onDelete
            )
        }
    }*/
}


@Composable
fun ListaItemRow(item:Item,
              onItemClick: (Int) -> Unit,
              onDelete: (Item) -> Unit) {

    val context = LocalContext.current
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(15.dp),
        elevation = 10.dp)

    {
        Column() {
                Text(
                    item.descrip,
                    modifier = Modifier
                        .fillMaxWidth()

                )

        Row() {
            mostarMiniatura(item.imagen)

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Precio: ${item.price.toString()}$",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }

            IconButton(
                onClick = { deleteConfirmationRequired = true }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Borrar")
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDelete(item)
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false }
                )
            }

        }
    }

}

@Composable
fun mostarMiniatura(bitmap: Bitmap?) {

    //val uri = Uri.parse(stfrinUri)

    Column(

       /* Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),*/

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





    @Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}
