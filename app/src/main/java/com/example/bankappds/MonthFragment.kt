package com.example.bankappds

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMonthBinding
import com.example.bankappds.viewmodel.dataViewModel
import java.time.LocalDateTime

var ele = 11
class MonthFragment : Fragment() {

    var binding: FragmentMonthBinding? = null
    val viewModel: dataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentTime = LocalDateTime.now()

        val mon = currentTime.month

        var temp = mutableListOf<Expenditure>()
        binding?.txtListMonth?.text = currentTime.month.toString()


/*
        for ((K,V) in viewModel.expenditureMap.value?.toMutableMap()){
            if (K.substring(4,6) == ele.toString()){
                for (expd in V) temp.add(expd)
            }
        }*/

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = ExpenditureAdapter(temp)

    }

}