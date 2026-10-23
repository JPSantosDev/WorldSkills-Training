package com.example.simuladoa2refeito.ui.repository

object TokenManager {
    var token: String = ""
        private set

    var user: String = ""
        private set


    fun editUser(name: String){
        this.user = name
    }


    fun clearToken(){
        this.token = ""
    }

    fun editToken(token: String){
        this.token = token
    }


}