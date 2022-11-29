package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bankappds.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    var binding: FragmentSearchBinding? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)

        return binding?.root
    }


}