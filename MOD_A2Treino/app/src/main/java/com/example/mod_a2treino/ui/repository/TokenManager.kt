package com.example.mod_a2treino.ui.repository

object TokenManager {

    var token: String? = null
        private  set

    fun setToken(token: String){
        this.token = token
    }
    fun clearToken(){
        this.token = null
    }
}