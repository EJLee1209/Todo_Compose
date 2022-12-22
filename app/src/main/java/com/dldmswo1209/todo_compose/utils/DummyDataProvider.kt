package com.dldmswo1209.composebasic.utils

data class RandomUser(
    val name : String = "개발하는 이은재",
    val description: String = "Android Jetpack Compose!",
    val profileImage : String = "https://randomuser.me/api/portraits/men/23.jpg"
)

object DummyDataProvider {
    val userList = List<RandomUser>(200){ RandomUser() }
}