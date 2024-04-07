package com.example.gameapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gameapp.db.dao.GenreDao
import com.example.gameapp.db.entity.GenreItem

@Database(
    entities = [GenreItem::class],
    version = 1
)
abstract class GameRoom: RoomDatabase() {
    abstract val dao: GenreDao

    companion object {

        @Volatile
        private var INSTANCE: GameRoom? = null

        fun getInstance(context: Context): GameRoom {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameRoom::class.java,
                        "game_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}