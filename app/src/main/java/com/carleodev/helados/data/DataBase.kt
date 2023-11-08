package com.carleodev.helados.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [Item::class, Pagos::class, Ticket::class, Tasa::class],
    version = 4 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class HeladosDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun pagosDao():PagosDao
    abstract fun tasaDao():TasaDao
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var Instance: HeladosDatabase? = null

        fun getDatabase(context: Context): HeladosDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HeladosDatabase::class.java, "helados_data")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}