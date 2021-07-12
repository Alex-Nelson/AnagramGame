package com.example.anagramgame.score

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.anagramgame.R
import com.example.anagramgame.data.AppDatabase
import com.example.anagramgame.databinding.FragmentScoreListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


/**
 * A fragment to display the user's scores from previous games.
 */
class ScoreListFragment : Fragment() {

    lateinit var binding: FragmentScoreListBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_score_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).scoreDao

        val viewModelFactory = ScoreListViewModelFactory(dataSource)
        val listViewModel = ViewModelProvider(this, viewModelFactory)
                .get(ScoreListViewModel::class.java)

        binding.scoreListViewModel = listViewModel

        val adapter = ScoreAdapter()
        binding.scoreRecyclerView.adapter = adapter

        binding.lifecycleOwner = this

        listViewModel.scoreList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        // Add an Observer on the state variable when the scores have been deleted
        listViewModel.showSnackbarEventDelete.observe(viewLifecycleOwner, {
            if(it == true){

                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.delete_snackbar), Snackbar.LENGTH_SHORT)
                        .show()

                // Reset the state to make sure the toast is only shown once, even if the device
                // has a configuration change
                listViewModel.doneShowingSnackbar()
            }
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delet_scores_option -> {
                // Show an AlertDialog asking the user to confirm their
                // decision to delete the scores
                MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.delete_all))
                        .setMessage(getString(R.string.dialog_delete_warning))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.cancel), null)
                        // Delete all the scores if the user confirms their decision
                        .setPositiveButton(getString(R.string.delete)){_, _ ->
                            binding.scoreListViewModel?.deleteAllScores()
                        }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}