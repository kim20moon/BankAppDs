package com.example.bankappds

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bankappds.databinding.RankingListBinding
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("NotifyDataSetChanged")
class RankingAdapter
    : RecyclerView.Adapter<RankingAdapter.Holder>() {
    val userData : ArrayList<Person> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {  // users의 문서를 불러온 뒤 person으로 변환해 ArrayList에 담는다
        db.collection("users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList를 초기화
            userData.clear()

            // userData 안에 cloud firestore에 담긴 모든 정보가 들어온다
            for (snapshot in querySnapshot!!.documents) {
                val item = snapshot.toObject(Person::class.java)
                userData.add(item!!)
            }
            notifyDataSetChanged()
        }
    }

    // 검색 기능을 사용하는 함수
    fun search(serachWord : String, option : String) {
        db.collection("users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            userData.clear()

            for (snapshot in querySnapshot!!.documents) {
                if (snapshot.getString(option)!!.contains(serachWord)) {
                    var item = snapshot.toObject(Person::class.java)
                    userData.add(item!!)
                }
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RankingListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(userData[position])
    }

    override fun getItemCount() = userData.size

    class Holder(private val binding : RankingListBinding): ViewHolder(binding.root) {
        fun bind(user: Person) {
            binding.txtPersonName.text = user.personName
            binding.txtTotalExpense.text = user.totalExpense.toString()
            binding.txtTotalRegExpense.text = user.totalRegExpense.toString()
        }
    }
}