package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var name: String,
    var apellido: String,
    var anio: Long =2020
)