package com.example.anagramgame.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {

    /** Insert a score */
    @Insert
    suspend fun insertScore(score: Score)

    /** Select a score from the table */
    @Query("SELECT * FROM score_table WHERE score_id = :id")
    suspend fun getScore(id: Int): Score

    /** Select all scores from the table */
    @Query("SELECT * FROM score_table")
    fun getAllScores(): LiveData<List<Score>>

    /** Select the most recent score */
    @Query("SELECT * FROM score_table ORDER BY score_id DESC LIMIT 1")
    suspend fun getLastScore(): Score

    /** Delete all scores from the table */
    @Query("DELETE FROM score_table")
    suspend fun deleteScores()
}