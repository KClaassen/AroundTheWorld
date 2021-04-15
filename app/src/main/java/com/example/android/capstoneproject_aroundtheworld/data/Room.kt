//package com.example.android.capstoneproject_aroundtheworld.data
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.room.*
//
//
//@Dao
//interface CountryDao {
//    //Loads all countries from databasecountry and returns them as a List
//    @Query("SELECT  * FROM databasecountry ORDER BY name ASC")
//    fun getCountries() : LiveData<List<DatabaseCountry>>
//
//    //Store values in cache
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(vararg countries: DatabaseCountry)
//}
//
//
//
//
//@Database(entities = [DatabaseCountry::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class CountryDatabase: RoomDatabase() {
//
//    abstract val countryDao: CountryDao
//
//    //companion object allows clients to access the methods for creating or getting the database without instantiating the class
//    companion object{
//
//        //@Volatile annotation means value of variable is always up to date and same to all execution threads
//        //Value of a volatile variable will never be cached and all writes and reads will be done to and from the main memory
//        //It means changes made by one thread to INSTANCE are visible to all other threads immediately.
//
//        @Volatile
//        private var INSTANCE: CountryDatabase? = null
//
//        fun getDatabase(context: Context): CountryDatabase {
//            synchronized(this){
//                var instance = INSTANCE
//
//                if(instance == null){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        CountryDatabase::class.java,
//                        "country_database")
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE =instance
//                }
//
//                return instance
//            }
//        }
//    }
//
//}