package com.example.bankappds.repository

import androidx.lifecycle.MutableLiveData
import com.example.bankappds.Expenditure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Repository {
    val database = Firebase.database
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    val emailRef = database.getReference("email")
    val nameRef= database.getReference("name")
    val expenditureMapRef = database.getReference("expenditureMap")
    val totalExpenseRef = database.getReference("totalExpense")
    val regExpenditureMapRef = database.getReference("regExpdMap")
    val totalRegExpenseRef = database.getReference("totalRegExpense")

    fun observeEmail(email: MutableLiveData<String>) {
        emailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeExpenditureMap(expenditureMap: MutableLiveData<MutableMap<String, MutableList<Expenditure>>?>) {
        expenditureMapRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeName(name: MutableLiveData<String>) {
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeTotalExpense(totalExpense: MutableLiveData<Int>) {
        totalExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun observeTotalRegExpense(totalRegExpense: MutableLiveData<Int>) {
        totalRegExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalRegExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postEmail(newValue: String) {
        emailRef.setValue(newValue)
    }
    fun postName(newValue: String) {
        nameRef.setValue(newValue)
    }

    fun postExpenditureMap(email: String, newValue: MutableMap<String, MutableList<Expenditure>>?) {
        expenditureMapRef.setValue(newValue)

        db.collection("users")
            .document(email)
            .update("expenditureMap", newValue)
    }
    fun postTotalExpense(email: String, newValue: Int) {
        totalExpenseRef.setValue(newValue)

        db.collection("users")
            .document(email)
            .update("totalExpense", newValue)
    }
    fun postRegExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?) {
        regExpenditureMapRef.setValue(newValue)
    }
    fun postTotalRegExpense(email: String, newValue: Int) {
        totalRegExpenseRef.setValue(newValue)

        db.collection("users")
            .document(email)
            .update("totalRegExpense", newValue)
    }
}