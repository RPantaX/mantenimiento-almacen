package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Sales


@Dao
interface SalesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sales: Sales)
    @Update
    suspend fun update(sales: Sales)
    @Delete
    suspend fun delete(sales: Sales)

    @Query("SELECT * FROM sales_table")
    suspend fun getAllSales(): List<Sales>
}