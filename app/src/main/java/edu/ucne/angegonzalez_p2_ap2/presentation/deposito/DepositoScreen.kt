package edu.ucne.angegonzalez_p2_ap2.presentation.deposito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    depositoId: Int,
    goBackToList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DepositoBodyScreen(
        depositoId = depositoId,
        viewModel = viewModel,
        uiState = uiState,
        goBackToList = goBackToList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBodyScreen(
    depositoId: Int,
    viewModel: DepositoViewModel,
    uiState: DepositoUiState,
    goBackToList: () -> Unit
) {
    LaunchedEffect(depositoId) {
        if (depositoId > 0) viewModel.find(depositoId)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (depositoId > 0) "Editar" else "Registrar",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Nombre") },
                        value = uiState.concepto,
                        onValueChange = viewModel::onConceptoChange,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    OutlinedTextField(
                        label = { Text(text = "Monto") },
                        value = uiState.monto,
                        onValueChange = viewModel::onMontoChange,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    OutlinedTextField(
                        label = { Text(text = "Cuenta") },
                        value = uiState.idCuenta.toString(),
                        onValueChange = viewModel::onCuentaChange,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(onClick = { goBackToList() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back"
                            )
                            Text(text = "Atrás")
                        }
                        OutlinedButton(onClick = {
                            if (depositoId > 0) {
                                viewModel.delete(depositoId)
                                goBackToList()
                            } else {
                                viewModel.new()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = if (depositoId > 0) "Borrar" else "Limpiar"
                            )
                            Text(text = if (depositoId > 0) "Borrar" else "Limpiar")
                        }
                        OutlinedButton(
                            onClick = {
                                if (viewModel.isValidate()) {
                                    if (depositoId > 0) viewModel.update() else viewModel.save()
                                    //goBackToList()
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save button"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }
        }
    }
}
