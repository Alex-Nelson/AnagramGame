package com.example.anagramgame.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anagramgame.data.ScoreDao

class ScoreListViewModelFactory(private val dataSource: ScoreDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreListViewModel::class.java)) {
            return ScoreListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}