package com.example.anagramgame.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for a [Score]. Keeps track of the final score, the number of words skipped, and the
 * difficulty chosen.
 * */
@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "score_id")
    val scoreId: Int,

    @ColumnInfo(name = "correct_score")
    val correctScore: Int,

    @ColumnInfo(name = "skipped")
    val skipped: Int,

    @ColumnInfo(name = "game_difficulty")
    val gameDifficulty: String
)

