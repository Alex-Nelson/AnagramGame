package com.example.anagramgame.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectDiffViewModel : ViewModel() {

    private var _difficulty = MutableLiveData<String>()
    val difficulty: LiveData<String> get() = _difficulty

    /** Navigation state variables */
    private var _navigateToGame = MutableLiveData<String>()
    val navigateToGame: LiveData<String> get() = _navigateToGame

    /**
     * Set the diffculty based on the radio button that is selected
     * */
    fun setDifficulty(gameDifficulty: String) {
        _difficulty.value = gameDifficulty
    }

    /**
     * Set up the navigation by saving the difficulty
     * */
    fun onPlay(){
        _navigateToGame.value = difficulty.value
    }

    fun doneNavigating(){
        _navigateToGame.value = null
    }
}