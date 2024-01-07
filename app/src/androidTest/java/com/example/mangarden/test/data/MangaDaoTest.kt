package com.example.mangarden.test.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mangarden.data.database.MangaDao
import com.example.mangarden.data.database.MangaDatabase
import com.example.mangarden.test.fakers.FakeDataSource
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class MangaDaoTest {
    private lateinit var mangaDao: MangaDao
    private lateinit var mangaDatabase: MangaDatabase
    private var mangaEntityList = FakeDataSource.databaseEntries
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        mangaDatabase = Room.inMemoryDatabaseBuilder(context, MangaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mangaDao = mangaDatabase.mangaDao()
    }

    @Test
    fun insertMangaList() {
        runBlocking {
            addMangaListToDatabase()
            val mangaList = mangaDao.getMangaList()
            assert(mangaList == mangaEntityList)
        }
    }

    @Test
    fun updateManga() {
        runBlocking {
            addMangaListToDatabase()
            mangaDao.updateManga("1", 1)
            val mangaList = mangaDao.getMangaList()
            assert(mangaList[0].chaptersRead == 1)
        }
    }

    @Test
    fun deleteManga() {
        runBlocking {
            addMangaListToDatabase()
            mangaDao.deleteManga("1")
            val mangaList = mangaDao.getMangaList()
            assert(mangaList.contains(mangaEntityList[0]).not())
        }
    }

    @Test
    fun getChaptersRead() {
        runBlocking {
            addMangaListToDatabase()
            val chaptersRead = mangaDao.getChaptersRead("1")
            assert(chaptersRead == 0)
        }
    }



        private suspend fun addMangaListToDatabase() {
            mangaEntityList.forEach { mangaDao.insertManga(it) }
        }



}