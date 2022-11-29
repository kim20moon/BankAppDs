package com.example.bankappds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankappds.Expenditure
import com.example.bankappds.repository.Repository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.exp

class dataViewModel: ViewModel() {
    // 뷰모델은 데이터 자료구조를 가지고 있음
    // 뷰모델은 프레그먼트를 직접 보지 않는다.
    private val repository = Repository()

    // 내부적으로는 바꿀수있는 라이브데이터
    private val _totalExpense = MutableLiveData<Int>(0)
    // 하지만 밖에서는 바꿀수없는 라이브데이터 - 일종의 패턴임임
    val totalExpense : LiveData<Int> get() = _totalExpense

    private val _totalRegExpense = MutableLiveData<Int>(0)
    val totalRegExpense : LiveData<Int> get() = _totalRegExpense

    private val _expenditureMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>?>()
    val expenditureMap : LiveData<MutableMap<String, MutableList<Expenditure>>?> get() = _expenditureMap

    private val _regExpdMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val regExpdMap : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _regExpdMap

    private val _email = MutableLiveData<String>("")
    val email : LiveData<String> get() = _email

    private val _password = MutableLiveData<String>("")
    val password : LiveData<String> get() = _password

    private val _personName = MutableLiveData<String> ("")
    val personName : LiveData<String> get() = _personName

    var tempExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
    var tempRegExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()

    init {
        repository.observeEmail(_email)
        repository.observePassword(_password)
        repository.observePersonName(_personName)
        repository.observeTotalExpense(_totalExpense)
        repository.observeTotalRegExpense(_totalRegExpense)
    }

    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }

    fun addExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (expenditureMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempExpdMap[dayInfo]?.add(expd)
            _expenditureMap.value = tempExpdMap
            println("Add Expenditure ${_expenditureMap.value}")
        } else { // 기존 값 존재 X
            tempExpdMap[dayInfo] = mutableListOf(expd)
            _expenditureMap.value = tempExpdMap
            println("Add Expenditure ${_expenditureMap.value}")
        }
        _totalExpense.value = _totalExpense.value?.plus(expd.expense)
        repository.postExpenditureMap(_expenditureMap.value)
        repository.postTotalExpense(email.value ?: "", _totalExpense.value ?: 0)
    }

    fun deleteExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempExpdMap[dayInfo]?.remove(expd)
        println("delte ${tempExpdMap.values}")
        _expenditureMap.value = tempExpdMap
        println("Delete Expenditrue ${_expenditureMap.value}")

        _totalExpense.value = _totalExpense.value?.minus(expd.expense)
        repository.postExpenditureMap( _expenditureMap.value)
        repository.postTotalExpense(email.value ?: "", _totalExpense.value ?: 0)
    }

    fun addRegExpenditure(expd: Expenditure){
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (regExpdMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempRegExpdMap[dayInfo]?.add(expd)
            _regExpdMap.value = tempRegExpdMap
            println("Add RegExpenditure ${_regExpdMap.value}")

        } else { // 기존 값 존재 X
            tempRegExpdMap[dayInfo] = mutableListOf(expd)
            _regExpdMap.value = tempRegExpdMap
            println("Add RegExpenditure ${_regExpdMap.value}")

        }
        _totalRegExpense.value = _totalRegExpense.value?.plus(expd.expense)

        repository.postRegExpenditureMap( _regExpdMap.value)
        repository.postTotalRegExpense(email.value ?: "", _totalRegExpense.value ?: 0)
    }

    fun deleteRegExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempRegExpdMap[dayInfo]?.remove(expd)
        _regExpdMap.value = tempExpdMap
        println("Delete RegExpenditure ${_regExpdMap.value}")

        _totalRegExpense.value = _totalRegExpense.value?.minus(expd.expense)

        repository.postRegExpenditureMap( _regExpdMap.value)
        repository.postTotalRegExpense(email.value ?: "",_totalRegExpense.value ?: 0)
    }

    fun plusEmail(data: String) {
        _email.value = data
        repository.postEmail(_email.value ?: "")
    }

    fun plusPersonName(data: String) {
        _personName.value = data
        repository.postPersonName(_personName.value ?: "")
    }

    fun plusMasterExpense(totalExpenseData: Number, totalRegExpenseData: Number) {
        _totalExpense.value = totalExpenseData.toInt()
        _totalRegExpense.value = totalRegExpenseData.toInt()
        repository.postMasterExpense(_totalExpense.value ?: 0, _totalRegExpense.value ?: 0)
    }

}
