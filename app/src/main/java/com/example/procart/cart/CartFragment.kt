package com.example.procart.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.procart.databinding.FragmentCartBinding
import com.example.procart.overview.ProductGridAdapter


class CartFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: ProductGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val binding = FragmentCartBinding.inflate(inflater)
        binding.lifecycleOwner = this
        adapter = ProductGridAdapter(
            sharedViewModel = sharedViewModel,
            onProductClick = { product ->
                findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToDetailsFragment(
                        product
                    )
                )
            }
        )
        binding.cartRecyclerView.adapter = adapter
        sharedViewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            cartItems?.let {
                adapter.submitList(it.toList())
            }
        })
        binding.backIconBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}
