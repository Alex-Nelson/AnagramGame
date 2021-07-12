package com.example.anagramgame.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Word::class, Score::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val wordDao: WordDao
    abstract val scoreDao: ScoreDao

    companion object {

        val GAME_DATA = listOf(Word(0, "act", "Easy"),
                Word(0, "age", "Easy"),
                Word(0, "air", "Easy"),
                Word(0, "bed", "Easy"),
                Word(0, "box", "Easy"),
                Word(0, "cold", "Easy"),
                Word(0, "dark", "Easy"),
                Word(0, "dog", "Easy"),
                Word(0, "end", "Easy"),
                Word(0, "firm", "Easy"),
                Word(0, "foot", "Easy"),
                Word(0, "game", "Easy"),
                Word(0, "hold", "Easy"),
                Word(0, "issue", "Easy"),
                Word(0, "item", "Easy"),
                Word(0, "kind", "Easy"),
                Word(0, "live", "Easy"),
                Word(0, "local", "Easy"),
                Word(0, "many", "Easy"),
                Word(0, "near", "Easy"),
                Word(0, "open", "Easy"),
                Word(0, "past", "Easy"),
                Word(0, "poor", "Easy"),
                Word(0, "pretty", "Easy"),
                Word(0, "really", "Easy"),
                Word(0, "should", "Easy"),
                Word(0, "south", "Easy"),
                Word(0, "system", "Easy"),
                Word(0, "very", "Easy"),
                Word(0, "voice", "Easy"),
                Word(0, "cite", "Easy"),
                Word(0, "ability", "Medium"),
                Word(0, "address", "Medium"),
                Word(0, "almost", "Medium"),
                Word(0, "budget", "Medium"),
                Word(0, "citizen", "Medium"),
                Word(0, "detail", "Medium"),
                Word(0, "exist", "Medium"),
                Word(0, "foreign", "Medium"),
                Word(0, "growth", "Medium"),
                Word(0, "health", "Medium"),
                Word(0, "hospital", "Medium"),
                Word(0, "involve", "Medium"),
                Word(0, "lawyer", "Medium"),
                Word(0, "measure", "Medium"),
                Word(0, "network", "Medium"),
                Word(0, "niche", "Medium"),
                Word(0, "option", "Medium"),
                Word(0, "partner", "Medium"),
                Word(0, "practice", "Medium"),
                Word(0, "pressure", "Medium"),
                Word(0, "rate", "Medium"),
                Word(0, "remain", "Medium"),
                Word(0, "site", "Medium"),
                Word(0, "theory", "Medium"),
                Word(0, "throw", "Medium"),
                Word(0, "violence", "Medium"),
                Word(0, "weight", "Medium"),
                Word(0, "whom", "Medium"),
                Word(0, "knight", "Medium"),
                Word(0, "multitude", "Medium"),
                Word(0, "cede", "Medium"),
                Word(0, "contrive", "Medium"),
                Word(0, "audience", "Hard"),
                Word(0, "although", "Hard"),
                Word(0, "administration", "Hard"),
                Word(0, "character", "Hard"),
                Word(0, "development", "Hard"),
                Word(0, "environment", "Hard"),
                Word(0, "experience", "Hard"),
                Word(0, "government", "Hard"),
                Word(0, "however", "Hard"),
                Word(0, "knowledge", "Hard"),
                Word(0, "international", "Hard"),
                Word(0, "management", "Hard"),
                Word(0, "necessary", "Hard"),
                Word(0, "opportunity", "Hard"),
                Word(0, "performance", "Hard"),
                Word(0, "quite", "Hard"),
                Word(0, "require", "Hard"),
                Word(0, "shoulder", "Hard"),
                Word(0, "structure", "Hard"),
                Word(0, "scarce", "Hard"),
                Word(0, "technology", "Hard"),
                Word(0, "territory", "Hard"),
                Word(0, "notwithstanding", "Hard"),
                Word(0, "ascertain", "Hard"),
                Word(0, "perpetual", "Hard"),
                Word(0, "pious", "Hard"),
                Word(0, "conspicuous", "Hard"),
                Word(0, "exalt", "Hard"),
                Word(0, "wont", "Hard"),
                Word(0, "fathom", "Hard"),
                Word(0, "rhetoric", "Hard"),
                Word(0, "wistful", "Hard"),
                Word(0, "caprice", "Hard"))

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "app_database")
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    // Insert the data on the IO Thread
                                    ioThread {
                                        getInstance(context).wordDao.insertWords(GAME_DATA)
                                    }
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

//        private fun buildDatabase(context: Context) =
//                Room.databaseBuilder(context.applicationContext,
//                        AppDatabase::class.java, "app_database")
//                        .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                // Insert the data on the IO Thread
//                                ioThread {
//                                    getInstance(context).wordDao.insertWords(GAME_DATA)
//                                }
//                            }
//                        })
//                        .build()
    }

}