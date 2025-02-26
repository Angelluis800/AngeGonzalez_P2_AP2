package edu.ucne.angegonzalez_p2_ap2.presentation.deposito

import java.util.Date

data class DepositoUiState(
    val idDeposito: Int = 0,
    val fecha: Date? = null,
    val idCuenta: Int = 0,
    val concepto: String = "",
    val monto: Double = 0.0,
)