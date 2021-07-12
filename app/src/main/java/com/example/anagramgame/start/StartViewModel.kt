package com.example.anagramgame.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {

    /** Navigation state variables */
    private var _navigateToSelectDiff = MutableLiveData<Boolean>()
    val navigateToSelectDiff: LiveData<Boolean> get() = _navigateToSelectDiff

    fun onNewGame() {
        _navigateToSelectDiff.value = true
    }

    fun doneNavigating() {
        _navigateToSelectDiff.value = false
    }
}