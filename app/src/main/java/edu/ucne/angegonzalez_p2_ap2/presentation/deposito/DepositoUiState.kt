package edu.ucne.angegonzalez_p2_ap2.presentation.deposito

import edu.ucne.angegonzalez_p2_ap2.data.remote.dto.DepositoDto
import java.util.Date

data class DepositoUiState(
    val idDeposito: Int = 0,
    val fecha: String = "",
    val idCuenta: String = "",
    val concepto: String = "",
    val monto: String = "",
    val listaDepositos: List<DepositoDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

)