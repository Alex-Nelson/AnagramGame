package com.example.anagramgame.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for a [Word]. It keeps track of the word and the difficulty.
 * */
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    val wordId: Int,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: String
)
