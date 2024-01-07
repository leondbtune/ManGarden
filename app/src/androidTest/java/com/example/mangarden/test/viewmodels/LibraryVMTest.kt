package com.example.mangarden.test.viewmodels

import com.example.mangarden.test.fakers.FakeDataSource
import com.example.mangarden.test.fakers.FakeRepository
import com.example.mangarden.ui.screens.library.LibraryVM
import org.junit.Before
import org.junit.Test

class LibraryVMTest {
    private lateinit var libraryVM: LibraryVM
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        libraryVM = LibraryVM(fakeRepository)
    }

    @Test
    fun onMangaClickedTest() {
        libraryVM.onMangaClicked(FakeDataSource.searchResult.results[0])
        assert(libraryVM.highLightedMangaId == FakeDataSource.searchResult.results[0].id)
    }


}