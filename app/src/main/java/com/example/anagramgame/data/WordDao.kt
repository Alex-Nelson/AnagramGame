package com.example.anagramgame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {

    /** Insert a word */
    @Insert
    fun insertWords(data: List<Word>)

    /** Select a word from the table */
    @Query("SELECT word FROM word_table WHERE word_id = :id")
    suspend fun getWord(id: Int): String

    /** Select all words with a given difficulty */
    // TODO: May switch back to not suspending
    @Query("SELECT word_id FROM word_table WHERE difficulty = :diff")
    suspend fun getWords(diff: String): List<Int>
}