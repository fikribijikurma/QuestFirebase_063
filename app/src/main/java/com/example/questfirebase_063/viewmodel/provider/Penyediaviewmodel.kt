package com.example.questfirebase_063.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.questfirebase_063.repository.AplikasiDataSiswa
import com.example.questfirebase_063.viewmodel.Entryviewmodel
import com.example.questfirebase_063.viewmodel.Homeviewmodel

fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                AplikasiDataSiswa
        )
object Penyediaviewmodel{
    val Factory = viewModelFactory {
        initializer { Homeviewmodel(aplikasiDataSiswa().container.repositorySiswa) }
        initializer { Entryviewmodel(aplikasiDataSiswa().container.repositorySiswa) }
    }
}