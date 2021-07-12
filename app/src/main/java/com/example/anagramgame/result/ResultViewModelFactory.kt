package com.example.anagramgame.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anagramgame.data.ScoreDao

class ResultViewModelFactory(
        private val data: Array<String>, private val dataSource: ScoreDao) : ViewModelProvider.Factory {
   @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ResultViewModel::class.java)){
            return ResultViewModel(data, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}