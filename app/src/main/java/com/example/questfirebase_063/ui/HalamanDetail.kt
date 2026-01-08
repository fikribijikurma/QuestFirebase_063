package com.example.questfirebase_063.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questfirebase_063.R
import com.example.questfirebase_063.model.Siswa
import com.example.questfirebase_063.ui.theme.route.DestinasiDetail
import com.example.questfirebase_063.viewmodel.DetailViewModel
import com.example.questfirebase_063.viewmodel.StatusUIDetail
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.statusUIDetail

    Scaffold(
        topBar = {
            DetailTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val idSiswa = (uiState as? StatusUIDetail.Success)?.satusiswa?.id ?: 0
                    onEditClick(idSiswa.toLong())
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_siswa)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ItemDetailContainer(
            statusUIDetail = uiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDelete = {
                viewModel.viewModelScope.launch {
                    viewModel.hapusSatuSiswa()
                    navigateBack()
                }
            }
        )
    }
}

@Composable
private fun ItemDetailContainer(
    statusUIDetail: StatusUIDetail,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (statusUIDetail) {
        is StatusUIDetail.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is StatusUIDetail.Success -> {
            Column(
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

                ItemDetail(
                    siswa = statusUIDetail.satusiswa ?: Siswa(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { deleteConfirmationRequired = true },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.delete))
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDelete()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
        }
        is StatusUIDetail.Error -> {
            Text(text = "Terjadi Kesalahan", modifier = modifier)
        }
    }
}