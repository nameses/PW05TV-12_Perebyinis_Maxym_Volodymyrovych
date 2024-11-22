package com.lab5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBar() {
    var selectedTab by remember { mutableStateOf(NavigationTab.Comparing) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.Comparing,
                    onClick = { selectedTab = NavigationTab.Comparing },
                    icon = { Icon(Icons.Default.Settings, "Порівнняння 2 систем") },
                    label = { Text("Порівнняння 2 систем") }
                )
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.LoosesCalculation,
                    onClick = { selectedTab = NavigationTab.LoosesCalculation },
                    icon = { Icon(Icons.Default.Settings, "Розрахунок збитків") },
                    label = { Text("Розрахунок збитків") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedTab) {
                NavigationTab.Comparing -> CalculatorComparing()
                NavigationTab.LoosesCalculation -> ShortCircuitBusbarCalculator()
            }
        }
    }
}

enum class NavigationTab {
    Comparing,
    LoosesCalculation
}