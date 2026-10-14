package com.example.tudo.ui.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tudo.ui.models.D1.Curso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


val Context.coursePreferences by preferencesDataStore("course_preferences")

class CoursePreferences(private val context: Context){

    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }

    object Keys{
        val CURSOS = stringPreferencesKey("cursos")
    }

    val cursos: Flow<List<Curso>> = context.coursePreferences.data
        .map { preferences ->

            val dadosCurso = preferences[Keys.CURSOS]

            if (dadosCurso.isNullOrEmpty()){
                emptyList()
            } else{
                try {
                    jsonInstance.decodeFromString(dadosCurso)
                } catch (e: Exception){
                    emptyList()
                }
            }
        }

    suspend fun salvarCurso(curso: Curso){
        context.coursePreferences.edit { preferences ->
            val dadosCurso = preferences[Keys.CURSOS]

            val cursoAtuais =
                if (dadosCurso.isNullOrEmpty()){
                    emptyList()
                } else {
                    try {
                        jsonInstance.decodeFromString<List<Curso>>(dadosCurso)
                    } catch (e: Exception){
                        emptyList()
                    }
                }

            val cursosAtualizados = cursoAtuais+curso

            preferences[Keys.CURSOS] = jsonInstance.encodeToString(cursosAtualizados)
        }
    }
}