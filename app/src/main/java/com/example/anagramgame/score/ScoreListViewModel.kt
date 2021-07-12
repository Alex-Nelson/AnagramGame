package com.example.anagramgame.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anagramgame.data.ScoreDao
import kotlinx.coroutines.launch

class ScoreListViewModel (dataSource: ScoreDao) : ViewModel(){

    // Hold a reference to the AppDatabase via ScoreDao
    private val database = dataSource

    // List of scores
    var scoreList = database.getAllScores()

    /** State variables */
    private val _showSnackbarEventDelete = MutableLiveData<Boolean>()
    val showSnackbarEventDelete: LiveData<Boolean> get() = _showSnackbarEventDelete

    /**
     * Delete all scores from the database.
     * */
    fun deleteAllScores() {
        viewModelScope.launch {
            database.deleteScores()
            _showSnackbarEventDelete.value = true
        }
    }

    /** Called immediately after calling 'show()' on a toast */
    fun doneShowingSnackbar() {
        _showSnackbarEventDelete.value = false
    }
}