package com.example.simuladod1.ui.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.simuladod1.ui.models.Professor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

val Context.teacherPreference by preferencesDataStore("teacher_preference")

class TeacherPreference(private val context: Context) {

    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }
    object Keys{
        val PROFESSOR = stringPreferencesKey("professor")
    }

    val professores: Flow<List<Professor>> = context.teacherPreference.data
        .map { preferences ->
            val dadosProfessor = preferences[Keys.PROFESSOR]

            if (dadosProfessor.isNullOrEmpty()){
                emptyList()
            } else {
                try {
                    jsonInstance.decodeFromString(dadosProfessor)
                } catch (e: Exception){
                    emptyList()
                }
            }
        }

    suspend fun salvarProfessor(professor: Professor){
        context.teacherPreference.edit { preferences ->
            val dadosAtuais = preferences[Keys.PROFESSOR]

            val professoresAtuais = if (dadosAtuais.isNullOrEmpty()){
                emptyList()
            } else {
                try {
                    jsonInstance.decodeFromString<List<Professor>>(dadosAtuais)
                } catch (e: Exception){
                    emptyList()
                }
            }

            if (!professoresAtuais.contains(professor)){
                val professoresAtualizados = professoresAtuais + professor
                preferences[Keys.PROFESSOR] = jsonInstance.encodeToString(professoresAtualizados)
            }
        }
    }
}