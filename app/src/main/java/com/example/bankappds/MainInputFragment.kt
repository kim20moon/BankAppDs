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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankappds.databinding.FragmentMainInputBinding
import com.example.bankappds.viewmodel.dataViewModel
import com.google.firebase.firestore.FirebaseFirestore


class MainInputFragment : Fragment() {
    private var binding: FragmentMainInputBinding? = null
    var typeT : Ecategory? = null

    val viewModel: dataViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainInputBinding.inflate(inflater)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetting()

        // 달력 날짜 전달 받기
        val args : MainInputFragmentArgs by navArgs()

//        val year = args.calendarDate?.year
//        val month = args.calendarDate?.month
//        val day = args.calendarDate?.day

        // 입력창 달력 날짜 설정
        binding?.txtInputYear?.text = args.calendarDate[0].toString()
        binding?.txtInputMonth?.text = args.calendarDate[1].toString()
        binding?.txtInputDay?.text = args.calendarDate[2].toString()

        // 저장 버튼 클릭시 date, expense, category, memo 전달
        binding?.btnSave?.setOnClickListener {

            if (typeT == null || binding?.edtMoney?.text.toString().isEmpty()  || binding?.edtMemo?.text.toString().isEmpty() ) {
                Toast.makeText(requireContext(), "누락된 칸이 있습니다", Toast.LENGTH_SHORT).show()
            }
            else {
                val temp = Expenditure(args.calendarDate[0], args.calendarDate[1], args.calendarDate[2],
                    binding?.edtMoney?.text.toString().toIntOrNull()?:0,
                    typeT, binding?.edtMemo?.text.toString())

                viewModel.addExpenditure(temp)
                findNavController().navigate(R.id.action_mainInputFragment_to_mainFragment)
            }


        }
        // 뒤로가기
        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_mainInputFragment_to_mainFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // 카테고리 스피너 설정
    private fun spinnerSetting() {
        var spnLst = resources.getStringArray(R.array.category_type)
        //ArrayAdapter의 두 번쨰 인자는 스피너 목록에 아이템을 그려줄 레이아웃을 지정하여 줍니다.
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, spnLst)
        //activity_main에서 만들어 놓은 spinner에 adapter 연결하여 줍니다.
        binding?.spinnerInputCategory?.adapter = adapter
        //데이터가 들어가 있는 spinner 에서 선택한 아이템을 가져옵니다.
        binding?.spinnerInputCategory?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

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
    }

}