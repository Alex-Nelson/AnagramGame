package com.example.anagramgame.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anagramgame.data.WordDao

class GameViewModelFactory(
    private val diff: String, private val dataSource: WordDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(diff, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}