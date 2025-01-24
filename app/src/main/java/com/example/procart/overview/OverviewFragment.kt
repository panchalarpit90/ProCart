package com.example.procart.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.procart.R
import com.example.procart.cart.SharedViewModel
import com.example.procart.databinding.FragmentOverviewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils

class OverviewFragment : Fragment() {
    private lateinit var viewModel: OverviewViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: ProductGridAdapter

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = ProductGridAdapter(sharedViewModel) {
            viewModel.displayPropertyDetails(it)
        }
        binding.photosGrid.adapter = adapter
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer { product ->
            product?.let {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })
        val toolbar = binding.root.findViewById<MaterialToolbar>(R.id.topAppBar)
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            val badge = BadgeDrawable.create(requireContext())
            val cartItemCount = cartItems?.size ?: 0

            if (cartItemCount > 0) {
                badge.number = cartItemCount
                BadgeUtils.attachBadgeDrawable(badge, toolbar, R.id.menu_cart)
            } else {
                BadgeUtils.detachBadgeDrawable(badge, toolbar, R.id.menu_cart)
            }
        })
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.menu_cart) {
                findNavController().navigate(R.id.action_overviewFragment_to_cartFragment)
                true
            } else {
                false
            }
        }
        return binding.root
    }
}