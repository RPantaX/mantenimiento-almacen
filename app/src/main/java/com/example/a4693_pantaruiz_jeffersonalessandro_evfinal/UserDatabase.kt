package com.example.a4693_pantaruiz_jeffersonalessandro_evfinal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Inventory
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Provider
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.Sales
import com.example.a4693_pantaruiz_jeffersonalessandro_evfinal.model.User

@Database(entities = [User::class, Inventory::class, Provider::class, Sales::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun inventoryDao(): InventoryDAO
    abstract fun providerDao(): ProviderDAO
    abstract fun salesDao(): SalesDAO
    companion object {
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "invent_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}