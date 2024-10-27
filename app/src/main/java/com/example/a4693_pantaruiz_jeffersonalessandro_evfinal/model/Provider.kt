package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "provider_table")
data class Provider(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var providerDNI: String,
    var providerName: String,
    var direccion: String,
    var email: String,
    var products: String
)
