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
package com.example.cupcake.data

data class SpecOption(
    val name: String,
    val price: Double
)

object DataSource {
    val computerTypeOptions = listOf(
        SpecOption("Notebook Standard / Office", 4_500_000.0),
        SpecOption("Notebook Gaming", 8_500_000.0),
        SpecOption("PC Desktop Office / Studio", 3_500_000.0),
        SpecOption("PC Desktop Gaming Custom", 6_000_000.0)
    )

    val processorOptions = listOf(
        SpecOption("Intel Core i3-13100 (4 Core 3.4GHz)", 1_800_000.0),
        SpecOption("Intel Core i5-13400 (10 Core 2.5GHz)", 3_200_000.0),
        SpecOption("Intel Core i7-13700 (16 Core 2.1GHz)", 5_800_000.0),
        SpecOption("AMD Ryzen 5 5600 (6 Core 3.5GHz)", 2_100_000.0),
        SpecOption("AMD Ryzen 7 7700X (8 Core 4.5GHz)", 5_200_000.0)
    )

    val ramOptions = listOf(
        SpecOption("8 GB DDR4 / DDR5", 450_000.0),
        SpecOption("16 GB DDR4 / DDR5", 800_000.0),
        SpecOption("32 GB DDR5 High Speed", 1_600_000.0),
        SpecOption("64 GB DDR5 Dual Channel", 3_100_000.0)
    )

    val storageOptions = listOf(
        SpecOption("512 GB NVMe M.2 SSD", 650_000.0),
        SpecOption("1 TB NVMe M.2 SSD", 1_200_000.0),
        SpecOption("2 TB NVMe M.2 SSD High Speed", 2_300_000.0),
        SpecOption("1 TB HDD 7200 RPM + 256 GB SSD", 950_000.0)
    )

    val gpuOptions = listOf(
        SpecOption("Integrated Intel / AMD Graphics", 0.0),
        SpecOption("NVIDIA GeForce RTX 3050 6GB GDDR6", 3_200_000.0),
        SpecOption("NVIDIA GeForce RTX 4060 8GB GDDR6", 5_300_000.0),
        SpecOption("NVIDIA GeForce RTX 4070 12GB GDDR6X", 9_800_000.0),
        SpecOption("AMD Radeon RX 7600 8GB GDDR6", 4_600_000.0)
    )

    val monitorOptions = listOf(
        SpecOption("14.0\" Full HD Display (Bawaan Notebook)", 0.0),
        SpecOption("15.6\" Full HD IPS 144Hz Display", 1_200_000.0),
        SpecOption("24\" Full HD IPS Monitor 75Hz", 1_850_000.0),
        SpecOption("27\" QHD IPS Gaming Monitor 165Hz", 3_600_000.0),
        SpecOption("32\" 4K UHD Curved Display 144Hz", 5_800_000.0)
    )
}
