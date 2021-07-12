package com.example.anagramgame.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.anagramgame.data.Score
import com.example.anagramgame.databinding.ListScoreItemBinding

/**
 * Adapter for the [RecyclerView] in [ScoreListFragment]. Displays [Score] data object.
 * */
class ScoreAdapter : ListAdapter<Score, ScoreAdapter.ScoreViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return  ScoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ScoreViewHolder private constructor(private val binding: ListScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: Score) {
            binding.score = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ScoreViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListScoreItemBinding.inflate(
                        layoutInflater, parent, false)
                return ScoreViewHolder(binding)
            }
        }
    }

}

class ScoreDiffCallback : DiffUtil.ItemCallback<Score>(){
    override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem.scoreId == newItem.scoreId
    }

    override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem == newItem
    }

}