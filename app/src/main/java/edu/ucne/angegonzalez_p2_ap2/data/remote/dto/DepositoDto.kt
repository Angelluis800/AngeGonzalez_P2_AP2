package edu.ucne.angegonzalez_p2_ap2.data.remote.dto

import java.util.Date

data class DepositoDto(
    val idDeposito: Int,
    val fecha: Date? = null,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double,
)