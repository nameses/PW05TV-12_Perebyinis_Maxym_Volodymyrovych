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
fun CalculatorComparing() {
    var onePhaseItem1 by remember { mutableStateOf("") }
    var onePhaseItem2 by remember { mutableStateOf("") }
    var onePhaseItem3 by remember { mutableStateOf("") }
    var onePhaseItem4 by remember { mutableStateOf("") }
    var onePhaseItem5 by remember { mutableStateOf("") }
    var twoPhaseItem by remember { mutableStateOf("") }

    var result1 by remember { mutableStateOf(0.0) }
    var result2 by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier.padding(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Одноколова система:")
        OutlinedTextField(
            value = onePhaseItem1,
            enabled = false,
            onValueChange = { onePhaseItem1 = it },
            label = { Text("Елегазовий вимикач 110 кВ") },
        )
        OutlinedTextField(
            value = onePhaseItem2,
            onValueChange = { onePhaseItem2 = it },
            label = { Text("Довжина ПЛ-110кВ в км") },
        )
        OutlinedTextField(
            value = onePhaseItem3,
            enabled = false,
            onValueChange = { onePhaseItem3 = it },
            label = { Text("Трансформатор 110/10 кВ") },
        )
        OutlinedTextField(
            value = onePhaseItem4,
            onValueChange = { onePhaseItem4 = it },
            enabled = false,
            label = { Text("Ввідний вимикач 10 кВ") },
        )
        OutlinedTextField(
            value = onePhaseItem5,
            onValueChange = { onePhaseItem5 = it },
            label = { Text("Кількість приєднань 10 кВ") },
        )
        Text("Двоколова система складається з двох ідентичних одноколових та:")
        OutlinedTextField(
            value = twoPhaseItem,
            onValueChange = { twoPhaseItem = it },
            enabled = false,
            label = { Text("Секційного вимикача 10 кВ") },
        )
        Button(
            onClick = {
                val results = calculate(
                    onePhaseItem2.toDoubleOrNull() ?: 0.0,
                    onePhaseItem5.toDoubleOrNull() ?: 0.0
                )
                result1 = results.first
                result2 = results.second
            }
        ) {
            Text("Обрахувати")
        }

        if (result1 != 0.0 && result2 != 0.0) {
            Text("Частота відмов одноколової системи: ${String.format("%.3f", result1)}")
            Text("Частота відмов двоколової системи: ${String.format("%.3f", result2)}")
            if (result1 < result2){
                Text("Надійність одноколової системи електропередачі є вищою ніж двоколової")
            } else if(result1 > result2) {
                Text("Надійність двоколової системи електропередачі є вищою ніж одноколової")
            } else {
                Text("Надійність двоколової та одноколової систем електропередачі однакові")
            }
        }
    }
}

fun calculate(quantityPl110kV: Double, quantityAttachments: Double): Pair<Double, Double> {

    val failureRate1 = 0.01
    val failureRate2 = 0.007 * quantityPl110kV
    var failureRate3 = 0.015
    val failureRate4 = 0.02
    val failureRate5 = 0.03 * quantityAttachments
    val failureRate6 = 0.02

    val duration1 = 30
    val duration2 = 10
    val duration3 = 100
    val duration4 = 15
    val duration5 = 2

    val resFaillureRate1 = failureRate1 + failureRate2 + failureRate3 + failureRate4 + failureRate5
    val averageRecoveryDuration = (failureRate1 * duration1 + failureRate2 * duration2 + failureRate3 * duration3
            + failureRate4 * duration4 + failureRate5 * duration5) / resFaillureRate1
    val ka = (resFaillureRate1 * averageRecoveryDuration) / 8760
    val kp = 1.2 * (43.0 / 8760.0)
    val resFaillureRate2 = 2 * resFaillureRate1 * (ka + kp)

    return Pair(resFaillureRate1, resFaillureRate2 + failureRate6)
}

