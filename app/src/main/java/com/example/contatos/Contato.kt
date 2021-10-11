package com.example.contatos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contato (
    val nome: String,
    val sobrenome: String,
    val telefone: Int
    ): Parcelable

