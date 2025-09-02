package com.project.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(1.0) }
    val oConversionFactor = remember { mutableStateOf(1.0) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        color = Color.Red
    )

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value)
            .roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = { Text("Enter Value") },
            textStyle = customTextStyle
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Input Unit Selector
            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Select Input Unit"
                    )
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    listOf(
                        "Centimeters" to 0.01,
                        "Meters" to 1.0,
                        "Feet" to 0.3048,
                        "Millimeters" to 0.001
                    ).forEach { (unit, factor) ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                iExpanded = false
                                inputUnit = unit
                                conversionFactor.value = factor
                                convertUnits()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Output Unit Selector
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Select Output Unit"
                    )
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    listOf(
                        "Centimeters" to 0.01,
                        "Meters" to 1.0,
                        "Feet" to 0.3048,
                        "Millimeters" to 0.001
                    ).forEach { (unit, factor) ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                oExpanded = false
                                outputUnit = unit
                                oConversionFactor.value = factor
                                convertUnits()
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}
