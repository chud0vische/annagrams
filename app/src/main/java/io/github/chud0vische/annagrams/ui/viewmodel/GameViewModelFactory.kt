package io.github.chud0vische.annagrams.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.chud0vische.annagrams.data.db.AppDatabase
import io.github.chud0vische.annagrams.data.repository.LevelRepository

class GameViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            val dao = AppDatabase.getDatabase(context).wordDao()
            val repository = LevelRepository(dao)

            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}