package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_table")
data class Inventory (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var productName: String,
    var description: String,
    var quantity: Int,
    var price: Double
)