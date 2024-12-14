package com.example.financialapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FinancialViewModel : ViewModel() {
    val result = mutableStateOf("")

    // Productos
    fun calculatePrecioVenta(base: Double) {
        updateResult("Precio con IVA: ${String.format("%.2f", base * 1.19)}")
    }

    fun calculateMargenGanancia(precioVenta: Double, costo: Double) {
        val margen = ((precioVenta - costo) / precioVenta) * 100
        updateResult("Margen de ganancia: ${String.format("%.2f", margen)}%")
    }

    fun calculatePuntoEquilibrio(costosFijos: Double, precioVenta: Double, costoVariable: Double) {
        val punto = costosFijos / (precioVenta - costoVariable)
        updateResult("Punto de equilibrio: ${String.format("%.2f", punto)}")
    }

    fun calculateROI(ingresos: Double, inversion: Double) {
        val roi = ((ingresos - inversion) / inversion) * 100
        updateResult("ROI: ${String.format("%.2f", roi)}%")
    }

    // Empleador
    fun calculateCostoNomina(base: Double) {
        updateResult("Costo total: ${String.format("%.2f", base * 1.555)}")
    }

    fun calculatePrestaciones(base: Double) {
        updateResult("Prestaciones sociales: ${String.format("%.2f", base * 0.205)}")
    }

    fun calculateAportesParafiscales(base: Double) {
        updateResult("Aportes parafiscales: ${String.format("%.2f", base * 0.09)}")
    }

    fun calculateSeguridadSocial(base: Double) {
        updateResult("Seguridad social: ${String.format("%.2f", base *  0.2183)}")
    }

    // Empleado
    fun calculateSalarioNeto(base: Double) {
        val pension = base * 0.04 // 4% Pensión
        val salud = base * 0.04   // 4% Salud
        val salarioNeto = base - (pension + salud)
        updateResult(
            "Salario neto: ${String.format("%.2f", salarioNeto)}\n" +
                    "Deducciones: Pensión 4% = ${String.format("%.2f", pension)}, Salud 4% = ${String.format("%.2f", salud)}"
        )
    }

    fun calculateHoraExtraDiurna(base: Double) {
        val valorHoraDiurna = (base / 240) * 1.25 // Hora extra diurna
        updateResult("Hora extra diurna: ${String.format("%.2f", valorHoraDiurna)}")
    }

    fun calculateHoraExtraNocturna(base: Double) {
        val valorHoraNocturna = (base / 240) * 1.75 // Hora extra nocturna
        updateResult("Hora extra nocturna: ${String.format("%.2f", valorHoraNocturna)}")
    }

    fun calculateHoraDominicalFestiva(base: Double) {
        val valorHoraFestiva = (base / 240) * 2.0 // Hora dominical o festiva
        updateResult("Hora dominical/festiva: ${String.format("%.2f", valorHoraFestiva)}")
    }


    // Actualiza el resultado centralizado
    private fun updateResult(value: String) {
        result.value = value
    }
}

