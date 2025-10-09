package com.mtovar.gastospersonales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import com.mtovar.gastospersonales.ui.screen.GastoScreen
import com.mtovar.gastospersonales.ui.theme.AppTheme
import com.mtovar.gastospersonales.viewmodel.GastoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel : GastoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
                GastoScreen(viewModel)
            }
        }
    }
}
