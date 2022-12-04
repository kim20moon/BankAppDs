package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentRankingBinding

class RankingFragment : Fragment() {
    var binding: FragmentRankingBinding? = null

    // 첫 화면에서 뜨는 spinner 목록은 이름
    var searchOption = "name"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // spinner를 이용한 검색 설정
        binding?.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding?.spinner?.getItemAtPosition(position)) {
                    // 이름으로 선택하면 이름을 검색
                    "이름" -> {
                        searchOption = "name"
                    }
                    // 총 일반 지출을 선택하면 총 일반 지출을 검색
                    "총 일반 지출" -> {
                        searchOption = "totalExpense"
                    }
                    // 총 정기 지출을 선택하면 총 정기 지출을 검색
                    "총 정기 지출" -> {
                        searchOption = "totalRegExpense"
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding?.recRanking?.layoutManager = LinearLayoutManager(context)
        binding?.recRanking?.adapter = RankingAdapter()

        binding?.searchBtn?.setOnClickListener {
            // RankingAdapter 내에 있는 search 함수를 불러와 검색 기능을 활성화
            (binding?.recRanking?.adapter as RankingAdapter).search(binding?.searchWord?.text.toString(), searchOption)
        }
    }
}