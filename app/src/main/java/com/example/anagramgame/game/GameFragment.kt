package com.example.anagramgame.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anagramgame.R
import com.example.anagramgame.data.AppDatabase
import com.example.anagramgame.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

/**
 * A [Fragment] where the user plays the game. It shows snackbars based on the user's response.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private var diff: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the difficulty from the Fragment arguments
        arguments?.let {
            diff = it.getString("difficulty").toString()
        }

//        hideKeyboard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).wordDao

        val viewModelFactory = GameViewModelFactory(diff, dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GameViewModel::class.java)

        // Set the viewModel for data binding - this allows the bound layout access to all the
        // data in the ViewModel
        binding.gameViewModel = viewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Save the user's answer when they edit the EditTextField
//        viewModel.setAnswer(binding.answerEditText.text)
//
//        binding.answerEditText.addTextChangedListener(afterTextChanged = viewModel.setAnswer(answer = )
//        )
        binding.answerEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                viewModel.setAnswer("")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setAnswer(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.setAnswer(s.toString())
            }
        })

        // Show a snackbar if the answer was correct
        viewModel.correctSnackbarEvent.observe(viewLifecycleOwner, {
            if (it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.correct_snackbar), Snackbar.LENGTH_SHORT).show()

                // Reset the state to make sure that the toast is only shown once, even if the
                // device has a configuration change
                viewModel.doneShowingSnackbar("Correct")
//                viewModel.doneShowingCorrectSnackbar()
            }
        })

        // Show a snackbar if the answer was incorrect
        viewModel.incorrectSnackbarEvent.observe(viewLifecycleOwner, {
            if (it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.incorrect_snackbar), Snackbar.LENGTH_SHORT).show()

                // Reset the state to make sure that the toast is only shown once, even if the
                // device has a configuration change
                viewModel.doneShowingSnackbar("Incorrect")
//                viewModel.doneShowingIncorrectSnackbar()
            }
        })

        // Show a snackbar if the user skipped
        viewModel.skippedSnackbarEvent.observe(viewLifecycleOwner, {
            if(it == true){
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.skipped_snackbar), Snackbar.LENGTH_SHORT).show()

                // Reset the state to make sure that the toast is only shown once, even if the
                // device has a configuration change
            viewModel.doneShowingSnackbar("Skipped")
//                viewModel.doneShowingSkipSnackbar()
            }
        })

        // Add an Observer to the state variable for Navigating when the Game is done
        viewModel.navigateToResult.observe(viewLifecycleOwner, {
            if (it == true) {
                val action = viewModel.finalResult.value
                if (action != null) {
                    this.findNavController()
                            .navigate(GameFragmentDirections
                                    .actionGameFragmentToResultFragment(action))

                    viewModel.doneNavigatingToResult()
                }
            }

        })

        setHasOptionsMenu(true)
    }

//    private fun hideKeyboard(){
//        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.quit_current_game -> {
                // Show an AlertDialog to allow user the option to quit the current game
                MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.exit_game))
                        .setMessage(getString(R.string.dialog_quit_warning))
                        .setCancelable(true)
                        .setNegativeButton(getString(R.string.cancel), null)
                        // Sends the user back to the starting fragment without saving
                        .setPositiveButton(getString(R.string.quit)){ _, _ ->
                            this.findNavController()
                                    .navigate(GameFragmentDirections
                                            .actionGameFragmentToStartFragment())}.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}