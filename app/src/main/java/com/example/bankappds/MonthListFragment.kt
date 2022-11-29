package com.example.bankappds

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMonthBinding
import com.example.bankappds.databinding.FragmentMonthListBinding
import java.time.LocalDateTime


class MonthListFragment : Fragment() {
    var binding: FragmentMonthListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentTime = LocalDateTime.now()

        val mon = currentTime.month

        var temp = mutableListOf<Expenditure>()
        binding?.txtNowmon?.text = currentTime.month.toString()


/*
        for ((K,V) in expenditureMap){
            if (K.substring(4,6) == ele.toString()){
                for (expd in V) temp.add(expd)
            }
        }
*/

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = ExpenditureAdapter(temp)

    }
}