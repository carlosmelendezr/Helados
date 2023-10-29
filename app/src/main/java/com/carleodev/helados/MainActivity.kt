package com.carleodev.helados

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import com.carleodev.helados.ui.theme.HeladosTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //Printooth.init(this);
        setContent {
            HeladosTheme {
                HeladosApp()

            }
        } }

}