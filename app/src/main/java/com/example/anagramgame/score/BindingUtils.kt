package com.example.anagramgame.score

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.anagramgame.R
import com.example.anagramgame.data.Score

@BindingAdapter("gameScore")
fun TextView.setGameScore(item: Score) {
    // "Total Score: %d"
    text = context.resources.getString(R.string.text_final_score, item.correctScore)
}

@BindingAdapter("skippedValue")
fun TextView.setSkippedValue(item: Score) {
    // "Skipped: %d words
    text = context.resources.getString(R.string.result_skipped, item.skipped)
}

@BindingAdapter("gameDifficulty")
fun TextView.setGameDifficulty(item: Score) {
    // "Difficulty: Medium
    text = context.resources.getString(R.string.text_game_difficulty, item.gameDifficulty)
}

@BindingAdapter("gameNumber")
fun TextView.setGameNumber(item: Score){
    // Game No. %d
    text = context.resources.getString(R.string.text_game_number, item.scoreId)
}