package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Provider

@Dao
interface ProviderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(provider: Provider)
    @Update
    suspend fun update(provider: Provider)
    @Delete
    suspend fun delete(provider: Provider)

    @Query("SELECT * FROM provider_table")
    suspend fun getAllProvider(): List<Provider>
}