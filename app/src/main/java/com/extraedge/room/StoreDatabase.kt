package com.extraedge.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.extraedge.mvvm.model.response.CategoryEntity
import com.extraedge.mvvm.model.response.CategoryTypeConverter

@Database(entities = [CategoryEntity::class], version = 1)
@TypeConverters(value = [CategoryTypeConverter::class])
abstract class StoreDatabase : RoomDatabase() {
    abstract fun getStoreDao(): StoreDao

    companion object {
        private var instance: StoreDatabase? = null

        fun getAppDatabase(context: Context): StoreDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, StoreDatabase::class.java, "StoreDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}