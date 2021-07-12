package com.example.anagramgame.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.*
import com.example.anagramgame.data.WordDao
import kotlinx.coroutines.launch

// Game ends after 20 words have been used
private const val GAME_ENDS = 20
private const val SCORE_VALUE = 10

/**
 * A [ViewModel] that handles the game's functionality.
 * */
class GameViewModel(private val diff: String, dataSource: WordDao) : ViewModel() {

    // Hold a reference to the AppDatabase via the WordDao
    private  val database = dataSource

    // List to hold all the ids for the words
    private var idList = mutableListOf<Int>()

    // List of word ids that have already been used
    private var seenIds = mutableListOf<Int>()

    /** UI values */
    private var _numWords = MutableLiveData<Int>()
    val numWords: LiveData<Int>
        get() = _numWords

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private var _skipped = MutableLiveData<Int>()
    val skipped: LiveData<Int>
        get() = _skipped

    private var _anagram = MutableLiveData<String>()
    val anagram: LiveData<Spannable> = Transformations.map(_anagram) {
        if(it == null){
            SpannableString("")
        }else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                    TtsSpan.VerbatimBuilder(scrambledWord).build(),
                    0,
                    scrambledWord.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    private var _currentWord = MutableLiveData<String>()
    private val currentWord: LiveData<String>
        get() = _currentWord

    private var _userAnswer = MutableLiveData<String>()
    private val userAnswer: LiveData<String>
        get() = _userAnswer

    /** Snackbar state variables */
    private var _correctSnackbarEvent = MutableLiveData<Boolean>()
    val correctSnackbarEvent: LiveData<Boolean>
        get() = _correctSnackbarEvent

    private var _incorrectSnackbarEvent = MutableLiveData<Boolean>()
    val incorrectSnackbarEvent: LiveData<Boolean>
        get() = _incorrectSnackbarEvent

    private var _skippedSnackbarEvent = MutableLiveData<Boolean>()
    val skippedSnackbarEvent: LiveData<Boolean>
        get() = _skippedSnackbarEvent

    /** Navigation state variable */
    private var _finalResult = MutableLiveData<Array<String>?>()
    val finalResult: LiveData<Array<String>?>
        get() = _finalResult

    private var _navigateToResult = MutableLiveData<Boolean>()
    val navigateToResult: LiveData<Boolean>
        get() = _navigateToResult

    init {
        _score.value = 0
        _skipped.value = 0
        _numWords.value = 0

        initializeIdList(diff)
    }

    /** Retrieve a list of ids to be used to get a word later */
    private fun initializeIdList(difficulty: String) {
        viewModelScope.launch {
            idList.addAll(database.getWords(difficulty))

            // Get the first word
            selectWord()
        }
    }

    /** Select a word from the id list that has not been used */
    private fun selectWord(){

        // Play the game if the user has not seen 20 words
        if (numWords.value!! < GAME_ENDS){
            var id: Int

            // Randomly select an id that has not been used from the list
            do {
                id = idList[(Math.random() * GAME_ENDS).toInt()]
            }while (seenIds.contains(id))

            // Add the selected id to the list so it can't be used again
            seenIds.add(id)
            _numWords.value = _numWords.value?.plus(1)

            // Call the database to retrieve the word
            viewModelScope.launch {
                _currentWord.value = database.getWord(id)
                generateAnagram()
            }
        } else {
            endGame()
        }
    }

    /** Generate an anagram for the user to solve */
    private fun generateAnagram(){
        val arr = currentWord.value?.toCharArray()

        // Randomly shuffle the letters in the word around until it's not the same string
        if(arr != null){
            do {
                arr.shuffle()
            } while (String(arr) == currentWord.value)
        }
//
//        val temp = arr?.let { String(it) }

        _anagram.value = arr?.let { String(it) }
    }

    /** Call this when the game ends */
    private fun endGame(){
        // Bundle up the values to send to the ResultFragment
        _finalResult.value = arrayOf(diff, score.value.toString(), skipped.value.toString())

        // Notify the fragment that it's ready to navigate
        _navigateToResult.value = true
    }

    /** Check if the user's answer is correct */
    fun onSubmit(){
        // Increment the score if the user's answer was correct
        if(userAnswer.value == currentWord.value) {
            _score.value = _score.value?.plus(SCORE_VALUE)
            _correctSnackbarEvent.value = true
        } else {
            _incorrectSnackbarEvent.value = true
        }

        // Reset the value to empty string
        _userAnswer.value = ""

        // Get the next word
        selectWord()
    }

    /** Increment the skip value if the user skips the word */
    fun onSkip() {
        _skipped.value = _skipped.value?.plus(1)
        _skippedSnackbarEvent.value = true

        // Reset the value to empty string
        _userAnswer.value = ""

        // Get the next word
        selectWord()
    }

    /** Save the user's answer */
    fun setAnswer(answer: String) {
        _userAnswer.value = answer
    }

    /** Reset the snackbar value based on which snackbar was shown */
    fun doneShowingSnackbar(snackbarEvent: String){
        when (snackbarEvent) {
            "Correct" -> {
                _correctSnackbarEvent.value = false
            }
            "Incorrect" -> {
                _incorrectSnackbarEvent.value = false
            }
            else -> {
                _skippedSnackbarEvent.value = false
            }
        }
    }

//    fun doneShowingCorrectSnackbar(){
//        _correctSnackbarEvent.value = false
//    }
//
//    fun doneShowingIncorrectSnackbar(){
//        _incorrectSnackbarEvent.value = false
//    }
//
//    fun doneShowingSkipSnackbar(){
//        _skippedSnackbarEvent.value = false
//    }

    /** Reset the state variables after the fragment is done navigating */
    fun doneNavigatingToResult(){
        _finalResult.value = null
        _navigateToResult.value = false
    }
}