package com.carleodev.helados

import android.app.Application
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf

import com.carleodev.helados.data.AppContainer
import com.carleodev.helados.data.AppDataContainer



class HeladosApplication: Application() {

    lateinit var container: AppContainer


    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }


}