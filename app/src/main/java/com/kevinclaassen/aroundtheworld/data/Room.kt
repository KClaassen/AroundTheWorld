package com.kevinclaassen.aroundtheworld.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.kevinclaassen.aroundtheworld.models.Country
import com.kevinclaassen.aroundtheworld.models.Trip

/**
 *  Countries
 */

@Dao
interface CountryDao {
    //Loads all countries from databasecountry and returns them as a List
    @Query("SELECT  * FROM country ORDER BY name ASC")
    fun getCountries() : LiveData<List<Country>>

    //Store values in cache
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(countries: List<Country>)

    //Update counter
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCountry(country: Country)
}




@Database(entities = [Country::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountryDatabase: RoomDatabase() {

    abstract val countryDao: CountryDao

    //companion object allows clients to access the methods for creating or getting the database without instantiating the class
    companion object{

        //@Volatile annotation means value of variable is always up to date and same to all execution threads
        //Value of a volatile variable will never be cached and all writes and reads will be done to and from the main memory
        //It means changes made by one thread to INSTANCE are visible to all other threads immediately.

        @Volatile
        private var INSTANCE: CountryDatabase? = null

        fun getDatabase(context: Context): CountryDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CountryDatabase::class.java,
                        "country_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }

                return instance
            }
        }
    }

}


/**
 *  Trips
 */

@Dao
interface TripDao {
    // Loads all trips and returns them as List
    // Not suspend as it's LiveData
    @Query("SELECT * FROM trip ORDER BY ID DESC")
    fun getAllTrips(): LiveData<MutableList<Trip>>

    // Insert a Trip in the database. If the trip already exists, replace it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrip(trip: Trip)

    @Query("SELECT * FROM trip WHERE id = :id")
    fun getTripById(id: Int): LiveData<Trip>

    //Store values in cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrips(vararg trip: Trip)

    //Update trip with Image
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTripImages(trip: Trip)

    // Delete a trip
    @Delete()
    fun deleteTrip(trip: Trip)
}



@Database(entities = [Trip::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TripDatabase: RoomDatabase() {

    abstract val tripDao: TripDao

    //companion object allows clients to access the methods for creating or getting the database without instantiating the class
    companion object{

        //@Volatile annotation means value of variable is always up to date and same to all execution threads
        //Value of a volatile variable will never be cached and all writes and reads will be done to and from the main memory
        //It means changes made by one thread to INSTANCE are visible to all other threads immediately.

        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TripDatabase::class.java,
                            "trip_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE =instance
                }

                return instance
            }
        }
    }

}