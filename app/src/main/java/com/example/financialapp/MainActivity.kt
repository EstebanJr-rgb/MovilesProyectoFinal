package com.example.financialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financialapp.viewModel.FinancialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinancialApp()
        }
    }
}

@Composable
fun FinancialApp(viewModel: FinancialViewModel = viewModel()) {
    var selectedCategory by remember { mutableStateOf("Productos") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo de la App
        LogoSection()

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Navegación
        NavigationSection(selectedCategory) { category ->
            selectedCategory = category
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido Dinámico
        when (selectedCategory) {
            "Productos" -> ProductOptions(viewModel)
            "Empleador" -> EmployerOptions(viewModel)
            "Empleado" -> EmployeeOptions(viewModel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resultado Calculado
        ResultSection(viewModel.result.value)
    }
}

@Composable
fun LogoSection() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFFFF9800), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun NavigationSection(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Productos", "Empleador", "Empleado").forEach { category ->
            Button(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == category) Color(0xFF3F51B5) else Color.LightGray
                )
            ) {
                Text(category, color = Color.White)
            }
        }
    }
}

@Composable
fun ResultSection(result: String) {
    if (result.isNotEmpty()) {
        Text(
            text = result,
            fontSize = 18.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductOptions(viewModel: FinancialViewModel) {
    ExpandableOption("Precio de Venta", listOf("Precio Base")) {
        viewModel.calculatePrecioVenta(it[0].toDouble())
    }
    ExpandableOption("Margen de Ganancia", listOf("Precio Venta", "Costo")) {
        viewModel.calculateMargenGanancia(it[0].toDouble(), it[1].toDouble())
    }
    ExpandableOption("Punto de Equilibrio", listOf("Costos Fijos", "Precio Venta", "Costo Variable")) {
        viewModel.calculatePuntoEquilibrio(it[0].toDouble(), it[1].toDouble(), it[2].toDouble())
    }
    ExpandableOption("ROI del Producto", listOf("Ingresos", "Inversión")) {
        viewModel.calculateROI(it[0].toDouble(), it[1].toDouble())
    }
}

@Composable
fun EmployerOptions(viewModel: FinancialViewModel) {
    ExpandableOption("Costo Total de Nómina", listOf("Costo Base")) {
        viewModel.calculateCostoNomina(it[0].toDouble())
    }
    ExpandableOption("Seguridad Social", listOf("Salario Base")) {
        viewModel.calculatePrestaciones(it[0].toDouble())
    }
    ExpandableOption("Aportes Parafiscales", listOf("Salario Base")) {
        viewModel.calculateAportesParafiscales(it[0].toDouble())
    }
    ExpandableOption("Prestaciones Sociales", listOf("Salario Base")) {
        viewModel.calculateSeguridadSocial(it[0].toDouble())
    }
}

@Composable
fun EmployeeOptions(viewModel: FinancialViewModel) {
    ExpandableOption("Salario Neto", listOf("Salario Base")) {
        viewModel.calculateSalarioNeto(it[0].toDouble())
    }
    ExpandableOption("Hora Extra Diurna", listOf("Salario Base")) {
        viewModel.calculateHoraExtraDiurna(it[0].toDouble())
    }
    ExpandableOption("Hora Extra Nocturna", listOf("Salario Base")) {
        viewModel.calculateHoraExtraNocturna(it[0].toDouble())
    }
    ExpandableOption("Hora Festiva/Dominical", listOf("Salario Base")) {
        viewModel.calculateHoraDominicalFestiva(it[0].toDouble())
    }
}

@Composable
fun ExpandableOption(title: String, inputs: List<String>, onCalculate: (List<String>) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    val values = remember { inputs.map { mutableStateOf("") } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse"
                )
            }

            if (isExpanded) {
                inputs.forEachIndexed { index, inputLabel ->
                    OutlinedTextField(
                        value = values[index].value,
                        onValueChange = { values[index].value = it },
                        label = { Text(inputLabel) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }

                Button(
                    onClick = { onCalculate(values.map { it.value }) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Calcular")
                }
            }
        }
    }
}

