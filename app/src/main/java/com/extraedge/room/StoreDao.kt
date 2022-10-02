package com.extraedge.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.extraedge.mvvm.model.response.CategoryEntity

@Dao
interface StoreDao {
    // insert data in database.
    @Insert
    fun insert(item: CategoryEntity)

    // all the data of database.
    @Query("SELECT * FROM store_items")
    fun getAllStoreItems(): LiveData<List<CategoryEntity>>
}