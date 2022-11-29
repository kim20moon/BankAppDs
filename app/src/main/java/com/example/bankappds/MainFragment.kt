package com.example.bankappds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentMainBinding
import com.example.bankappds.viewmodel.dataViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap


class MainFragment : Fragment() {
    private var binding : FragmentMainBinding? = null
    private var position = FIRST_POSITION
    val viewModel: dataViewModel by activityViewModels()

    companion object {
        const val FIRST_POSITION = 1
        const val SECOND_POSITION = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        if (!MyApplication.checkAuth()) {
            findNavController().navigate(R.id.action_mainFragment_to_loginLogoutActivity)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.personName.observe(viewLifecycleOwner) {
            binding?.txtPersonName?.text = viewModel.personName.value?.toString()
        }

        childFragmentManager.beginTransaction().replace(R.id.frm_fragment, DayCalendarFragment()).commit()

        binding?.imgBtnProfile?.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            when(position) {
                FIRST_POSITION -> {
                    transaction.replace(R.id.frm_fragment, MonthFragment()).commit()
                    position = SECOND_POSITION
                }
                SECOND_POSITION -> {
                    transaction.replace(R.id.frm_fragment, DayCalendarFragment()).commit()
                    position = FIRST_POSITION
                }
            }
        }
    }
}