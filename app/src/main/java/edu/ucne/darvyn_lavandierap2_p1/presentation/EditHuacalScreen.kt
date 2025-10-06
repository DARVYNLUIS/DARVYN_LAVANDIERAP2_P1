package edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuacalEditScreen(
    entradaId: Int?,
    navController: NavController,
    viewModel: EditHuacalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(entradaId) {
        entradaId?.let {
            viewModel.onEvent(EditHuacalUiEvent.Load(it))
        }
    }

    LaunchedEffect(state.message) {
        state.message?.let { msg ->
            scope.launch {
                snackbarHostState.showSnackbar(msg)
                viewModel.clearMessage()
            }
        }
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(if (state.isNew) "Nueva Entrada Huacal" else "Editar Entrada") })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            OutlinedTextField(
                value = state.nombreCliente,
                onValueChange = { viewModel.onEvent(EditHuacalUiEvent.NombreClienteChanged(it)) },
                label = { Text("Cliente") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { viewModel.onEvent(EditHuacalUiEvent.FechaChanged(it)) },
                label = { Text("Fecha (YYYY-MM-DD)") },
                isError = state.fechaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.fechaError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.cantidad,
                onValueChange = { viewModel.onEvent(EditHuacalUiEvent.CantidadChanged(it)) },
                label = { Text("Cantidad") },
                isError = state.cantidadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.cantidadError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.precio,
                onValueChange = { viewModel.onEvent(EditHuacalUiEvent.PrecioChanged(it)) },
                label = { Text("Precio") },
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.precioError?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(EditHuacalUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
