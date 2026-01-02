package com.example.questfirebase_063.ui.theme.route

import com.example.questfirebase_063.R

object DestinasiEdit : DestinasiNavigasi{
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}