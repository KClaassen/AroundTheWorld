package com.kevinclaassen.aroundtheworld.countries.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kevinclaassen.aroundtheworld.R
import com.kevinclaassen.aroundtheworld.databinding.FragmentCountryDetailBinding

class CountryDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentCountryDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_country_detail, container, false)
        binding.lifecycleOwner = this

        val arguments = CountryDetailFragmentArgs.fromBundle(requireArguments()).country
        binding.country = arguments
        binding.currency = arguments.currencies[0]
        binding.language = arguments.languages[0]


        return binding.root
    }

}