package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemperatureConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }

                }
        }
    }
}

@Composable
fun MyApp() {
    // Creating an instance of ViewModelForTemperatures class
    val viewModel: ViewModelForTemperatures = viewModel()

    // Display the user interface
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.convertedTemperature,
        convertTemp = { viewModel.convertTemperature(it) },
        toggleSwitch = { viewModel.doSwitchToggle() }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    toggleSwitch: () -> Unit) {

    // Define UI elements here
    var inputTextState by remember { mutableStateOf("") }

    fun onTextChange(newValue: String) {
        inputTextState = newValue
    }

    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = "Temperature Conversion App")
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(Color.White),

        ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Switch(checked = isFahrenheit,
                onCheckedChange = { toggleSwitch() })
            OutlinedTextField(
                value = inputTextState,
                onValueChange = { onTextChange(it) },
                label = { Text("Enter temperature") },
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = {
                    Text(text = if (isFahrenheit) "\u2109" else "\u2103")
                })
            } // End of Row
        } // End of Card
        Text(text = "Result: $result",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),)
        Button(onClick = { convertTemp(inputTextState) }) {
            Text(text = if (isFahrenheit) "Convert to Celsius" else "Convert to Fahrenheit")
        }
        
    }

}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TemperatureConverterTheme {
        MyApp()
    }
}