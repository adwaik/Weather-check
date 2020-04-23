package com.dwaik.weathercheck.model.room.dao

import androidx.room.*
import com.dwaik.weathercheck.model.entity.City
import io.reactivex.Maybe

@Dao
interface CityDao {

    @Query("SELECT * FROM cities")
    fun getAll(): Maybe<City>

    @Query("SELECT * FROM cities WHERE lower(name)= lower(:name)")
    fun get(name: String): Maybe<City>

    @Query("SELECT * FROM cities WHERE name LIKE lower(:name)")
    fun find(name: String): Maybe<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<City>)

    @Delete
    fun delete(item: City)

    @Delete
    fun delete(list: List<City>)

    @Query("DELETE FROM cities")
    fun deleteAll()
}