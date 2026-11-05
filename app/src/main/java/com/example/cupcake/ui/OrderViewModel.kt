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
package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.data.SpecOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale

/**
 * [OrderViewModel] holds information about a computer/notebook configuration order.
 * It calculates the estimated price based on selected processor, RAM, storage,
 * graphics card, monitor, and computer type.
 */
class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setComputerType(type: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(computerType = type)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun setProcessor(processor: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(processor = processor)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun setRam(ram: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(ram = ram)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun setStorage(storage: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(storage = storage)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun setGpu(gpu: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(gpu = gpu)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun setMonitor(monitor: SpecOption) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(monitor = monitor)
            updatedState.copy(price = calculatePrice(updatedState))
        }
    }

    fun resetOrder() {
        _uiState.value = OrderUiState()
    }

    private fun calculatePrice(state: OrderUiState): String {
        var total = 0.0
        state.computerType?.let { total += it.price }
        state.processor?.let { total += it.price }
        state.ram?.let { total += it.price }
        state.storage?.let { total += it.price }
        state.gpu?.let { total += it.price }
        state.monitor?.let { total += it.price }

        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        return formatter.format(total)
    }
}
