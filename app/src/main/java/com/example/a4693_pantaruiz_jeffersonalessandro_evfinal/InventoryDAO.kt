package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Inventory

@Dao
interface InventoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: Inventory)
    @Update
    suspend fun update(inventory: Inventory)
    @Delete
    suspend fun delete(inventory: Inventory)

    @Query("SELECT * FROM inventory_table")
    suspend fun getAllInventory(): List<Inventory>
}