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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.data.SpecOption
import com.example.cupcake.ui.components.FormattedPriceLabel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SpecOptionRow(
    option: SpecOption,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    formatter.maximumFractionDigits = 0
    val priceTag = if (option.price == 0.0) "Termasuk (Rp 0)" else "+ " + formatter.format(option.price)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelect
            )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onSelect
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = option.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = priceTag,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun DualSectionSelectScreen(
    subtotal: String,
    section1Title: String,
    section1Options: List<SpecOption>,
    selectedOption1: SpecOption?,
    onSelection1Changed: (SpecOption) -> Unit,
    section2Title: String,
    section2Options: List<SpecOption>,
    selectedOption2: SpecOption?,
    onSelection2Changed: (SpecOption) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = section1Title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            section1Options.forEach { option ->
                SpecOptionRow(
                    option = option,
                    selected = selectedOption1 == option,
                    onSelect = { onSelection1Changed(option) }
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            Text(
                text = section2Title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            section2Options.forEach { option ->
                SpecOptionRow(
                    option = option,
                    selected = selectedOption2 == option,
                    onSelect = { onSelection2Changed(option) }
                )
            }
        }

        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            FormattedPriceLabel(
                subtotal = subtotal,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = (selectedOption1 != null) && (selectedOption2 != null),
                    onClick = onNextButtonClicked
                ) {
                    Text(stringResource(R.string.next))
                }
            }
        }
    }
}

@Composable
fun SingleSectionSelectScreen(
    subtotal: String,
    sectionTitle: String,
    options: List<SpecOption>,
    selectedOption: SpecOption?,
    onSelectionChanged: (SpecOption) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            options.forEach { option ->
                SpecOptionRow(
                    option = option,
                    selected = selectedOption == option,
                    onSelect = { onSelectionChanged(option) }
                )
            }
        }

        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            FormattedPriceLabel(
                subtotal = subtotal,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = selectedOption != null,
                    onClick = onNextButtonClicked
                ) {
                    Text(stringResource(R.string.next))
                }
            }
        }
    }
}
