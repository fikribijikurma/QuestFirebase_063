package com.example.questfirebase_063.viewmodel

import com.example.questfirebase_063.model.Siswa

sealed interface StatusUIDetail {
    data class Success(val satusiswa: Siswa?) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}