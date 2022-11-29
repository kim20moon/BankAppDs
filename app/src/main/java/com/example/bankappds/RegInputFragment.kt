package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentRegInputBinding
import com.example.bankappds.viewmodel.dataViewModel


class RegInputFragment : Fragment() {
    var binding : FragmentRegInputBinding ?= null
    var typeT : Ecategory? = null
    val viewModel: dataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegInputBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spnLst = resources.getStringArray(R.array.category_type)
        //ArrayAdapter의 두 번쨰 인자는 스피너 목록에 아이템을 그려줄 레이아웃을 지정하여 줍니다.
        val adapter = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, spnLst)
        }
        //activity_main에서 만들어 놓은 spinner에 adapter 연결하여 줍니다.
        binding?.spinner?.adapter = adapter
        //데이터가 들어가 있는 spinner 에서 선택한 아이템을 가져옵니다.
        binding?.spinner?.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //position은 선택한 아이템의 위치를 넘겨주는 인자입니다.
                typeT = when(spnLst[position]){
                    "분류" -> null
                    "식비" ->Ecategory.FOOD
                    "금융" ->Ecategory.FINANCE
                    "쇼핑" ->Ecategory.SHOPPING
                    "여가" ->Ecategory.ENTERTAIN
                    "취미" ->Ecategory.HOBBY
                    "건강" ->Ecategory.HEALTH
                    "주거" ->Ecategory.HOME
                    "기타" ->Ecategory.ETC
                    else -> null
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding?.btnFin?.setOnClickListener {
            if (typeT == null || binding?.edtDay?.text.toString().isEmpty() || binding?.edtDay?.text.toString().isEmpty() || binding?.edtDay?.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "누락된 부분이 있습니다", Toast.LENGTH_SHORT).show()
            }
            else {
                val temp = Expenditure(0, 0, binding?.edtDay?.text.toString().toIntOrNull()?:0,
                    binding?.edtPay?.text.toString().toIntOrNull()?:0,
                    typeT, binding?.edtMemoReg?.text.toString()
                )
                viewModel.addRegExpenditure(temp)
                findNavController().navigate(R.id.action_regInputFragment_to_regFragment)
            }
        }

        binding?.btnCancle?.setOnClickListener {
            findNavController().navigate(R.id.action_regInputFragment_to_regFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}