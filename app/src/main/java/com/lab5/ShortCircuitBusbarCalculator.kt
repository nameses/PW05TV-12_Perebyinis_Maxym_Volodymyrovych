package com.lab5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShortCircuitBusbarCalculator() {
    var lossesEmergency by remember { mutableStateOf("") }
    var lossesPlanned by remember { mutableStateOf("") }

    var result by remember { mutableStateOf(0.0) }

    Column(
    modifier = Modifier.padding(all = 15.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Питомі збитки від переривання електропостачання: ")
        OutlinedTextField(
            value = lossesEmergency,
            onValueChange = { lossesEmergency = it },
            label = { Text("у разі аварійних вимкнень") },
        )
        OutlinedTextField(
            value = lossesPlanned,
            onValueChange = { lossesPlanned = it },
            label = { Text("у разі планових вимкнень") },
        )
        Button(
            onClick = {
                result = calculate2(
                    lossesEmergency.toDoubleOrNull() ?: 0.0,
                    lossesPlanned.toDoubleOrNull() ?: 0.0
                )
            }
        ) {
            Text("Обрахувати")
        }
        // Веведення результату
        if (result != 0.0) {
            Text("Матиматичне сподівання збитків від переривання " +
                    "електропостачання становить ${String.format("%.2f", result)} грн")
        }
    }
}

fun calculate2(lossesEmergency: Double, lossesPlanned: Double): Double {
    val mEmergency = 0.01 * 0.045 * 5120 * 6451
    val mPlanned = 0.004 * 5120 * 6451

    return lossesEmergency * mEmergency + lossesPlanned * mPlanned
}