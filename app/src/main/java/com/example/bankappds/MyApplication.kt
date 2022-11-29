package com.example.bankappds

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth

        // 로그인 확인 여부로 사용하는 변수
        var email: String? = null

        // 자동 로그인 기능을 활용하는 함수
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email

                // 이메일이 등록된 경우
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        // auth 선언
        auth = Firebase.auth
    }
}