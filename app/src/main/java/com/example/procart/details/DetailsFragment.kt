package com.example.procart.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.procart.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val selectedProduct = DetailsFragmentArgs.fromBundle(requireArguments()).selectedProduct
        val viewModelFactory = DetailsViewModelFactory(selectedProduct, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.iconImage.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}