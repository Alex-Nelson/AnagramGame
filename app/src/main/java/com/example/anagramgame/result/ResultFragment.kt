package com.example.anagramgame.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anagramgame.R
import com.example.anagramgame.data.AppDatabase
import com.example.anagramgame.databinding.FragmentResultBinding


/**
 * A [Fragment] that displays the game's final results to the user and allows them to play again or
 * quit.
 */
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private lateinit var data: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the data passed in from the Fragment arguments
        arguments?.let {
            val tempArr = it.getStringArray("resultsArray") as Array<String>
            data = tempArr.copyOf()

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).scoreDao

        val viewModelFactory = ResultViewModelFactory(data, dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(ResultViewModel::class.java)

        // Set the viewModel for data binding - this allows the bound layout access to all the
        // data in the ViewModel
        binding.resultViewModel = viewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Add an obsever for navigating to SelectDifficultyFragment
        viewModel.navigateToSelectDiff.observe(viewLifecycleOwner, {
            if(it == true){
                this.findNavController()
                        .navigate(ResultFragmentDirections
                                .actionResultFragmentToSelectDiffFragment())

                viewModel.doneNavigating("Play Again")
            }
        })

        // Add an observer for navigating to StartFragment
        viewModel.navigateToStart.observe(viewLifecycleOwner, {
            if(it == true){
                this.findNavController()
                        .navigate(ResultFragmentDirections
                                .actionResultFragmentToStartFragment())

                viewModel.doneNavigating("Quit")
            }
        })
    }
}