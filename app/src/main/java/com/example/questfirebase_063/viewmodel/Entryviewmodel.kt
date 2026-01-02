package com.example.questfirebase_063.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.questfirebase_063.model.DetailSiswa
import com.example.questfirebase_063.model.UIStateSiswa
import com.example.questfirebase_063.model.toDataSiswa
import com.example.questfirebase_063.repository.RepositorySiswa

class Entryviewmodel(private val repositorySiswa: RepositorySiswa): ViewModel(){
    var uiStateSiswa by mutableStateOf(UIStateSiswa())

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean
    {
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    fun updateUIState(detailSiswa: DetailSiswa){
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }
    suspend fun addSiswa(){
        if (validasiInput()){
            repositorySiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
        }
    }
}
