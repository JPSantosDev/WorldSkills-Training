package com.example.mod_d1treino.ui.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mod_d1treino.ui.models.Curso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.courseDataStore by preferencesDataStore("course_preferences")

class CoursePreferences(val context: Context){
    val json = Json {
        ignoreUnknownKeys = true

    }

    object Keys{
        val CURSOS = stringPreferencesKey("cursos")
    }

    val cursos: Flow<List<Curso>> = context.courseDataStore.data.map { preferences ->
        val atual = preferences[Keys.CURSOS]

        if (atual.isNullOrBlank()) {
            emptyList()
        } else {
            try {
                json.decodeFromString(atual)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun salvarCurso(curso: Curso) = context.courseDataStore.edit { preferences ->

        val dadosAtuais = preferences[Keys.CURSOS]
        val cursosAtuais =
            if (dadosAtuais.isNullOrBlank()) {
                emptyList()
            } else {
                try {
                    json.decodeFromString<List<Curso>>(dadosAtuais)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        val cursosAtualizados = cursosAtuais+curso

        preferences[Keys.CURSOS] = json.encodeToString(cursosAtualizados)

    }
}