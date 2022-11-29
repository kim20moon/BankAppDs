package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentRegBinding
import com.example.bankappds.viewmodel.dataViewModel


class RegFragment : Fragment() {
    var binding : FragmentRegBinding?= null
    val viewModel: dataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegBinding.inflate(inflater)

        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recPay?.layoutManager = LinearLayoutManager(activity) //context
        binding?.recPay?.setHasFixedSize(true)

        // RegMap 데이터 가져오기



        val adpatList = mutableListOf<Expenditure>()

        val temp = viewModel.regExpdMap.value?.toMap()
        if (temp != null){
            for ((K,V) in temp){
                for (expd in V) {
                    adpatList.add(expd)
                }
            }
        }

        viewModel.totalRegExpense.observe(viewLifecycleOwner) {
            binding?.totalReg?.text = viewModel.totalRegExpense.value?.toString()
            println("totalRegExpense ${viewModel.totalRegExpense.value}")
        }

        val adapter = ExpenditureAdapter(adpatList)
        binding?.recPay?.adapter = adapter

        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_regFragment_to_regInputFragment)
        }
        // 리사이클러뷰 객체 선택시 포지션 전달 받을 변수
        var selectedReg = -1
        // 삭제 버튼 클릭시
        binding?.btnDelete?.setOnClickListener {
            if (selectedReg != -1) {
                viewModel.deleteRegExpenditure(adpatList[selectedReg]) // map에서 리스트 삭제
                adpatList.removeAt(selectedReg)

                //adapter.notifyItemRemoved(selectedReg) // 삭제되었음을 알림
                adapter.notifyDataSetChanged()
                selectedReg = -1
            } else Toast.makeText(requireContext(), "삭제 할 고정지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }

        // 리사이클러뷰 객체 선택시 포지션 전달
        adapter.setItemClickListener(object : ExpenditureAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                selectedReg = position
                println("$selectedReg 번 선택")
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    /*
    @SuppressLint("NotifyDataSetChanged")
    private fun getInputData() {
        val args : RegFragmentArgs by navArgs()
        if ( args.regExpenditure?.expense != null && args.regExpenditure?.expense != 0 ) {
            val day = args.regExpenditure?.day
            val expense = args.regExpenditure?.expense
            val category: Ecategory? = args.regExpenditure?.category
            val memo = args.regExpenditure?.memo.toString()

            binding?.recPay?.layoutManager = LinearLayoutManager(activity) //context
            binding?.recPay?.setHasFixedSize(true)
            binding?.recPay?.adapter=ExpenditureAdapter((activity as MainActivity).expenditureMap[day]!!)

            // 메인 리스트에 추가
            (activity as MainActivity).addExpenditure(Expenditure(0, 0, day!!, expense!!, category!!, memo))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }

        else if ( args.regExpenditure?.expense == 0 ) {
            Toast.makeText(requireContext(), "추가할 지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

     */

}