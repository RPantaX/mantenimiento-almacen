package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "sales_table")
data class Sales(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var description: String,
    var quantity: Int,
    var products: String,
    var price: Double,
    var dniCustomer: String
)
