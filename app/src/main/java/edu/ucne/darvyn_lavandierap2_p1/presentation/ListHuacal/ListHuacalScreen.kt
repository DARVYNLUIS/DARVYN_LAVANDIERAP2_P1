package edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHuacalScreen(
    viewModel: ListHuacalViewModel = hiltViewModel(),
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var filtroVisible by remember { mutableStateOf(false) }

    val cantidadRegistros = state.entradas.size
    val totalPrecios = state.entradas.sumOf { it.precio }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HUACALES") },
                actions = {
                    IconButton(onClick = { filtroVisible = !filtroVisible }) {
                        Icon(Icons.Default.Menu, contentDescription = "Mostrar filtros")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (filtroVisible) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = state.clienteFilter ?: "",
                            onValueChange = { viewModel.onClienteFilterChanged(it) },
                            label = { Text("Filtrar por cliente") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = state.cantidadFilter?.toString() ?: "",
                            onValueChange = { viewModel.onCantidadFilterChanged(it) },
                            label = { Text("Filtrar por cantidad") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }

            if (state.entradas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "LISTA VACÍA... INGRESE UNA ENTRADA",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.entradas) { entrada ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onNavigateToEdit(entrada.idEntrada) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = entrada.nombreCliente,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text("Cantidad: ${entrada.cantidad}")
                                    Text("Precio: ${entrada.precio}")
                                    Text("Fecha: ${entrada.fecha}")
                                }
                                IconButton(onClick = {
                                    scope.launch {
                                        viewModel.deleteEntrada(entrada.idEntrada) { message ->
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message)
                                            }
                                        }
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }

                FloatingActionButton(
                    onClick = onNavigateToCreate,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp, bottom = 8.dp)
                ) {
                    Text("+")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("Cantidad de registros: $cantidadRegistros")
                        Text("Total de precios: $totalPrecios")
                    }
                }
            }
        }
    }
}
