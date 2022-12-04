package com.example.bankappds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bankappds.databinding.ActivityLoginLogoutBinding
import com.example.bankappds.viewmodel.dataViewModel
import com.google.firebase.firestore.FirebaseFirestore

class LoginLogoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginLogoutBinding
    val viewModel: dataViewModel by viewModels()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 상태인 경우
        if(MyApplication.checkAuth()) changeVisibility("login")
        // 로그아웃 상태인 경우
        else changeVisibility("logout")

        // 로그아웃 과정
        binding.btnLogout.setOnClickListener {
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")
        }

        // 회원가입 화면으로 이동
        binding.btnGoSignIn.setOnClickListener {
            // 회원가입 화면으로 변경
            changeVisibility("signIn")
        }

        // 회원가입 과정
        binding.btnSign.setOnClickListener {
            // 회원가입에 필요한 이메일, 비밀번호를 불러오기
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()
            val name: String = binding.authPersonNameEditView.text.toString()

            // 이메일 인증 과정
            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    binding.authPersonNameEditView.text.clear()

                    if (task.isSuccessful) {
                        // 해당 이메일로 가입 확인 문자 보내기
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(baseContext,
                                        "회원가입에 성공했습니다. 전송된 메일을 확인해주세요.",
                                        Toast.LENGTH_SHORT).show()

                                    val user = hashMapOf(
                                        "email" to email,
                                        "name" to name,
                                        "totalExpense" to 0,
                                        "totalRegExpense" to 0
                                    )

                                    db.collection("users")
                                        .document(email)
                                        .set(user)

                                    // 로그아웃 화면으로 변경
                                    changeVisibility("logout")
                                }
                                else {
                                    Toast.makeText(baseContext,
                                        "메일 전송에 실패했습니다.",
                                        Toast.LENGTH_SHORT).show()
                                    // 로그아웃 화면으로 변경
                                    changeVisibility("logout")
                                }
                            }
                    }
                    else { Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        // 로그아웃 화면으로 변경
                        changeVisibility("logout")
                    }
                }
        }

        // 로그인 과정
        binding.btnLogin.setOnClickListener {
            // 입력한 이메일과 비밀번호 불러오기
            val email: String = binding.authEmailEditView.text.toString()
            val password: String = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()

                    // 로그인 성공한 경우
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            changeVisibility("login")

                            viewModel.plusEmail(email)

                            db.collection("users")
                                .document(email)
                                .get()
                                .addOnSuccessListener { documentSnapshot ->
                                    val name: String = documentSnapshot.get("name") as String
                                    val totalExpense: Number = documentSnapshot.get("totalExpense") as Number
                                    val totalRegExpense: Number = documentSnapshot.get("totalRegExpense") as Number
                                    viewModel.plusName(name)
                                    viewModel.plusAllExpense(totalExpense, totalRegExpense)
                                }

                            val nextIntent = Intent(this, MainActivity::class.java)
                            startActivity(nextIntent)
                        }
                        // 이메일 인증이 안 된 경우
                        else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    // 로그인 실패한 경우
                    else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    fun changeVisibility(mode: String) {
        // 로그인 상태인 경우
        if(mode === "login") {
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                btnLogout.visibility = View.VISIBLE
                btnGoSignIn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                authPersonNameEditView.visibility = View.GONE
                btnSign.visibility = View.GONE
                btnLogin.visibility = View.GONE
            }
        }

        // 로그아웃 상태인 경우
        else if (mode === "logout") {
            binding.run {
                authMainTextView.text = "로그인하거나 회원가입 해주세요"
                btnLogout.visibility = View.GONE
                btnGoSignIn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                authPersonNameEditView.visibility = View.GONE
                btnSign.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }

        //회원가입 버튼을 누른 경우
        else if (mode === "signIn") {
            binding.run {
                btnLogout.visibility = View.GONE
                btnGoSignIn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                authPersonNameEditView.visibility = View.VISIBLE
                btnSign.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }
        }
    }
}