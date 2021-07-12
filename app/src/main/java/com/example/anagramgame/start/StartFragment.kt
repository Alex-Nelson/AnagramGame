package com.example.anagramgame.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.anagramgame.R
import com.example.anagramgame.databinding.FragmentStartBinding


/**
 * A simple [Fragment] where the user can start a new game
 */
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_start, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Specify the fragment as the lifecycle owner
        binding.lifecycleOwner = viewLifecycleOwner

        // Assign the view model to a property in the binding class
        binding.startViewModel = viewModel

        // Add an Observer to the state variable when navigating to select a difficulty
        viewModel.navigateToSelectDiff.observe(viewLifecycleOwner, {
            if(it == true){
                this.findNavController()
                        .navigate(StartFragmentDirections.actionStartFragmentToSelectDiffFragment())

                viewModel.doneNavigating()
            }
        })
    }

}