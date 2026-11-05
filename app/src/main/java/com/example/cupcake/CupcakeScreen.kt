/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.DualSectionSelectScreen
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SingleSectionSelectScreen
import com.example.cupcake.ui.StartOrderScreen

enum class CupcakeScreen(@get:StringRes val title: Int) {
    Start(title = R.string.app_name),
    ProcessorRam(title = R.string.choose_processor_ram),
    StorageGpu(title = R.string.choose_storage_gpu),
    Monitor(title = R.string.choose_monitor),
    Summary(title = R.string.order_summary)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    computerTypeOptions = DataSource.computerTypeOptions,
                    onNextButtonClicked = { selectedType ->
                        viewModel.setComputerType(selectedType)
                        navController.navigate(CupcakeScreen.ProcessorRam.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.ProcessorRam.name) {
                DualSectionSelectScreen(
                    subtotal = uiState.price,
                    section1Title = stringResource(R.string.processor),
                    section1Options = DataSource.processorOptions,
                    selectedOption1 = uiState.processor,
                    onSelection1Changed = { viewModel.setProcessor(it) },
                    section2Title = stringResource(R.string.ram),
                    section2Options = DataSource.ramOptions,
                    selectedOption2 = uiState.ram,
                    onSelection2Changed = { viewModel.setRam(it) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.StorageGpu.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.StorageGpu.name) {
                DualSectionSelectScreen(
                    subtotal = uiState.price,
                    section1Title = stringResource(R.string.storage),
                    section1Options = DataSource.storageOptions,
                    selectedOption1 = uiState.storage,
                    onSelection1Changed = { viewModel.setStorage(it) },
                    section2Title = stringResource(R.string.gpu),
                    section2Options = DataSource.gpuOptions,
                    selectedOption2 = uiState.gpu,
                    onSelection2Changed = { viewModel.setGpu(it) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Monitor.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.Monitor.name) {
                SingleSectionSelectScreen(
                    subtotal = uiState.price,
                    sectionTitle = stringResource(R.string.monitor),
                    options = DataSource.monitorOptions,
                    selectedOption = uiState.monitor,
                    onSelectionChanged = { viewModel.setMonitor(it) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject, summary ->
                        shareOrder(context, subject, summary)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_computer_config)
        )
    )
}
