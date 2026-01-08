package com.example.questfirebase_063.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questfirebase_063.R
import com.example.questfirebase_063.model.Siswa
import com.example.questfirebase_063.ui.theme.route.DestinasiHome
import com.example.questfirebase_063.viewmodel.Homeviewmodel
import com.example.questfirebase_063.viewmodel.StatusUiSiswa
import com.example.questfirebase_063.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Long) -> Unit,
    viewModel: Homeviewmodel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.entry_siswa)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            statusUiSiswa = viewModel.statusUiSiswa,
            onSiswaClick = onDetailClick,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    statusUiSiswa: StatusUiSiswa,
    onSiswaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        when (statusUiSiswa) {
            is StatusUiSiswa.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is StatusUiSiswa.Success -> {
                if (statusUiSiswa.siswa.isEmpty()) {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Tidak Ada Data Siswa")
                    }
                } else {
                    DaftarSiswa(
                        daftarSiswa = statusUiSiswa.siswa,
                        onSiswaClick = { onSiswaClick(it.id.toLong()) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            is StatusUiSiswa.Error -> OnError(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun DaftarSiswa(
    daftarSiswa: List<Siswa>,
    onSiswaClick: (Siswa) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(items = daftarSiswa, key = { it.id }) { siswa ->
            SiswaCard(
                siswa = siswa,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onSiswaClick(siswa) }
            )
        }
    }
}

@Composable
fun SiswaCard(
    siswa: Siswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}