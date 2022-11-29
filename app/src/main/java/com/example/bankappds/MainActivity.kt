package com.example.bankappds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


//TODO 화요일에 할거
//TODO 뷰모델로 데이터 관리

//TODO 프로필 - 원래는 로그인창이였다가 로그인 하면 프로필 뜨기
//TODO 통계 - 데이터 입력

//TODO 각 프래그먼트 xml 정리

//TODO 디자인은 마지막에

//TODO 앱 컨셉을 저축왕 느낌으로 게이지 채우는 형식
//TODO 노티피케이션 api 등 외부 기능

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        //action bar의 옛날이름
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout) //drawer layout을 사용할 경우 써줘야함
        //nav controll 할 때 top level에서는 표시하지 않게
        setupActionBarWithNavController(navController,appBarConfiguration) //네비게이션과 연결시킴
        binding.drawerNav.setupWithNavController(navController)
        setContentView(binding.root)

    }

    //up버튼에 대한 반응 세팅 - default-기본은 back 동작을 안함
    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}