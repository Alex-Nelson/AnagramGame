package com.example.anagramgame.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anagramgame.data.Score
import com.example.anagramgame.data.ScoreDao
import kotlinx.coroutines.launch

class ResultViewModel(data: Array<String>, dataSource: ScoreDao) : ViewModel() {

    private val database = dataSource

    private var _correct = MutableLiveData<Int>()
    val correct: LiveData<Int> get() = _correct

    private var _skipped = MutableLiveData<Int>()
    val skipped: LiveData<Int> get() = _skipped

    private var _diff = MutableLiveData<String>()
    val diff: LiveData<String> get() = _diff

    /** Navigation state variables */
    private var _navigateToSelectDiff = MutableLiveData<Boolean>()
    val navigateToSelectDiff: LiveData<Boolean> get() = _navigateToSelectDiff

    private var _navigateToStart = MutableLiveData<Boolean>()
    val navigateToStart: LiveData<Boolean> get() = _navigateToStart

    init {
        // Retrieve the values passed in from the GameFragment
        _diff.value = data[0]
        _correct.value = data[1].toInt()
        _skipped.value = data[2].toInt()

        insertScore()
    }

    /** Insert the most recent game's score into the database */
    private fun insertScore(){
        val newScore = Score(0, correct.value!!, skipped.value!!, diff.value!!)

        viewModelScope.launch {
            database.insertScore(newScore)
        }
    }

    fun onPlayAgain(){
        _navigateToSelectDiff.value = true
    }

    fun onQuit(){
        _navigateToStart.value = true
    }

    fun doneNavigating(type: String) {
        when(type) {
            "Play Again" -> {
                _navigateToSelectDiff.value = false
            }
            else -> {
                _navigateToStart.value = false
            }
        }
    }
}