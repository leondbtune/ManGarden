package com.example.mangarden.test.viewmodels

import com.example.mangarden.test.fakers.FakeDataSource
import com.example.mangarden.test.fakers.FakeRepository
import com.example.mangarden.ui.screens.search.SearchVM
import org.junit.Test

class SearchVMTest {
    private val searchVM = SearchVM(FakeRepository())

    @Test
    fun onQueryChangeTest() {
        searchVM.onQueryChange("test")
        assert(searchVM.query == "test")
    }

    @Test
    fun onMangaClickedTest() {
        searchVM.onMangaClicked(FakeDataSource.searchResult.results[0])
        assert(searchVM.highLightedMangaId == FakeDataSource.searchResult.results[0].id)
    }
}