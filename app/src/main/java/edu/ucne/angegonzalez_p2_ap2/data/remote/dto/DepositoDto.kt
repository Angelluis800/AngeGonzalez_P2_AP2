package edu.ucne.angegonzalez_p2_ap2.data.remote.dto

data class DepositoDto(
    val idDeposito: Int,
    val fecha: String,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double,
)