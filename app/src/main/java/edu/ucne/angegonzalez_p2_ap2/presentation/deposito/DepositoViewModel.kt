package edu.ucne.angegonzalez_p2_ap2.presentation.deposito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.angegonzalez_p2_ap2.data.remote.Resource
import edu.ucne.angegonzalez_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.angegonzalez_p2_ap2.data.repository.DepositoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoryRepository: DepositoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DepositoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllDepositos()
    }

    fun save() {
        viewModelScope.launch {
            if (isValidate()) {
                depositoryRepository.save(_uiState.value.toEntity()).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Resource.Success -> {
                            _uiState.update { it.copy(errorMessage = null, isLoading = false) }
                            new()
                            getAllDepositos()
                        }
                        is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message, isLoading = false) }
                    }
                }
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            depositoryRepository.delete(id).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null, isLoading = false) }
                        getAllDepositos()
                    }
                    is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message, isLoading = false) }
                }
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            depositoryRepository.update(
                _uiState.value.idDeposito, _uiState.value.toEntity()
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null, isLoading = false) }
                        getAllDepositos()
                    }
                    is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message, isLoading = false) }
                }
            }
        }
    }

    fun find(depositoId: Int) {
        viewModelScope.launch {
            if (depositoId > 0) {
                depositoryRepository.find(depositoId).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Resource.Success -> {
                            result.data?.let { dto ->
                                _uiState.update {
                                    it.copy(
                                        idDeposito = dto.idDeposito,
                                        fecha = dto.fecha,
                                        idCuenta = dto.idCuenta.toString(),
                                        concepto = dto.concepto,
                                        monto = dto.monto.toString(),
                                        isLoading = false
                                    )
                                }
                            }
                        }
                        is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message, isLoading = false) }
                    }
                }
            }
        }
    }

    fun getAllDepositos() {
        viewModelScope.launch {
            depositoryRepository.getAllDepositos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> _uiState.update {
                        it.copy(listaDepositos = result.data ?: emptyList(), isLoading = false)
                    }
                    is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message, isLoading = false) }
                }
            }
        }
    }

    fun new() {
        _uiState.value = DepositoUiState()
    }

    fun isValidate(): Boolean {
        val state = uiState.value
        return when {
            state.concepto.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "El Concepto es obligatoria") }
                false
            }
            state.monto <= 0.toString() -> {
                _uiState.update { it.copy(errorMessage = "El monto debe ser mayor a 0") }
                false
            }
            else -> true
        }
    }

    fun onCuentaChange(cuenta: String) {
        _uiState.update {
            val cuentaInt = cuenta.toInt()
            it.copy(
                idCuenta = cuenta,
                errorMessage = when {
                    cuentaInt <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onMontoChange(monto: String) {
        _uiState.update {
            val montoDouble = monto.toDouble()
            it.copy(
                monto = monto,
                errorMessage = when {
                    montoDouble <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }

    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(
                concepto = concepto,
                errorMessage = if (concepto.isBlank()) "El Concepto no puede estar vacía" else null
            )
        }
    }
}
fun getCurrentDate(): String {
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
    return sdf.format(java.util.Date())
}


fun DepositoUiState.toEntity() = DepositoDto(
    idDeposito = this.idDeposito,
    fecha = if (this.fecha.isNotBlank()) this.fecha else getCurrentDate(),
    idCuenta = this.idCuenta.toIntOrNull() ?: 0,
    concepto = this.concepto,
    monto = this.monto.toDoubleOrNull() ?: 0.0
)