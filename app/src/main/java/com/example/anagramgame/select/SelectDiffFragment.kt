package com.example.anagramgame.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.anagramgame.R
import com.example.anagramgame.databinding.FragmentSelectDiffBinding

/**
 * A [Fragment] that handles the user select the game's difficulty.
 */
class SelectDiffFragment : Fragment() {

    private lateinit var binding: FragmentSelectDiffBinding

    private val viewModel: SelectDiffViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_select_diff, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Specify the fragment as the lifecycle owner
        binding.lifecycleOwner = viewLifecycleOwner

        // Assign the view model to a property in the binding class
        binding.selectViewModel = viewModel

        // Add an Observer to the state variable when navigating to start a game
        viewModel.navigateToGame.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                        .navigate(SelectDiffFragmentDirections
                                .actionSelectDiffFragmentToGameFragment(it))

                viewModel.doneNavigating()
            }
        })
    }
}