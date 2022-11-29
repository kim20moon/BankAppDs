package com.example.bankappds.repository

import androidx.lifecycle.MutableLiveData
import com.example.bankappds.Expenditure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Suppress("UNCHECKED_CAST")
class Repository {
    val database = Firebase.database

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    val userRefEmail = database.getReference("user")
    val userRefPassword = database.getReference("password")
    val userRefPersonName = database.getReference("personName")
    val userRefExpenditureMap = database.getReference("expenditureMap")
    val userRefTotalExpense = database.getReference("totalExpense")
    val userRefRegExpenditureMap = database.getReference("regExpdMap")
    val userRefTotalRegExpense = database.getReference("totalRegExpense")

    fun observeEmail(email: MutableLiveData<String>) {
        userRefEmail.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observePassword(password: MutableLiveData<String>) {
        userRefPassword.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               password.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observePersonName(personName: MutableLiveData<String>) {
        userRefPersonName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                personName.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeTotalExpense(totalExpense: MutableLiveData<Int>) {
        userRefTotalExpense.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeTotalRegExpense(totalRegExpense: MutableLiveData<Int>) {
        userRefTotalRegExpense.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalRegExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postEmail(newvalue: String) {
        userRefEmail.setValue(newvalue)
    }

    fun postPersonName(newValue: String) {
        userRefPersonName.setValue(newValue)
    }

    fun postMasterExpense(totalExpense: Int, totalRegExpense: Int) {
        userRefTotalExpense.setValue(totalExpense)
        userRefTotalRegExpense.setValue(totalRegExpense)
    }

    fun postExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?) {
        userRefExpenditureMap.setValue(newValue)
    }

    fun postTotalExpense(email: String, newValue: Int) {
        userRefTotalExpense.setValue(newValue)

        db.collection("users")
            .document(email)
            .update("totalExpense", newValue)
    }

    fun postRegExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?) {
        userRefRegExpenditureMap.setValue(newValue)

    }

    fun postTotalRegExpense(email: String, newValue: Int) {
        userRefTotalRegExpense.setValue(newValue)

        db.collection("users")
            .document(email)
            .update("totalRegExpense", newValue)
    }
}