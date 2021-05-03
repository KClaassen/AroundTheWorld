package com.example.android.capstoneproject_aroundtheworld.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.capstoneproject_aroundtheworld.models.Country
import com.example.android.capstoneproject_aroundtheworld.models.Trip

/**
 *  Countries
 */

@Dao
interface CountryDao {
    //Loads all countries from databasecountry and returns them as a List
    @Query("SELECT  * FROM country ORDER BY name ASC")
    fun getCountries() : LiveData<List<Country>>

    //Store values in cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg countries: Country)
}




@Database(entities = [Country::class], version = 1, exportSchema = false)
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

//
///**
// *  Trips
// */
//
//@Dao
//interface TripDao {
//    // Loads all trips and returns them as List
//    @Query("SELECT * FROM trip ORDER BY dateFrom DESC")
//    suspend fun getAllTrips(): ArrayList<Trip>
//
//    // Insert a Trip in the database. If the trip already exists, replace it.
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveTrip(trip: Trip)
//
//    @Query("SELECT * FROM trip WHERE name = :tripName")
//    suspend fun getTripByName(tripName: String): Trip
//
//    //Store values in cache
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAllTrips(vararg trip: Trip)
//
//    // Delete all Trips
//    @Query("DELETE FROM trip")
//    suspend fun deleteAllTrips()
//}
//
//
//
//@Database(entities = [Trip::class], version = 1, exportSchema = false)
//abstract class TripDatabase: RoomDatabase() {
//
//    abstract val tripDao: TripDao
//
//    //companion object allows clients to access the methods for creating or getting the database without instantiating the class
//    companion object{
//
//        //@Volatile annotation means value of variable is always up to date and same to all execution threads
//        //Value of a volatile variable will never be cached and all writes and reads will be done to and from the main memory
//        //It means changes made by one thread to INSTANCE are visible to all other threads immediately.
//
//        @Volatile
//        private var INSTANCE: TripDatabase? = null
//
//        fun getDatabase(context: Context): TripDatabase {
//            synchronized(this){
//                var instance = INSTANCE
//
//                if(instance == null){
//                    instance = Room.databaseBuilder(
//                            context.applicationContext,
//                            TripDatabase::class.java,
//                            "trip_database")
//                            .fallbackToDestructiveMigration()
//                            .build()
//                    INSTANCE =instance
//                }
//
//                return instance
//            }
//        }
//    }
//
//}