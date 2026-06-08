package com.artmcar.wrarchive

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.usecase.warranty_uc.DeleteWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.GetAllWarrantiesUseCase
import com.artmcar.wrarchive.presentation.warranty.WarrantyEvent
import com.artmcar.wrarchive.presentation.warranty.WarrantyViewModel
import com.artmcar.wrarchive.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class WarrantyViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val getAllUseCase = mockk<GetAllWarrantiesUseCase>()
    private val deleteUseCase = mockk<DeleteWarrantyUseCase>(relaxed = true)
    private fun createWarranty(
        title: String,
        expiration: Long
    ) = WarrantyModel(
        localId = 1,
        remoteId = null,
        title = title,
        description = "",
        expirationDate = expiration,
        imagePath = null,
        createdAt = 0L,
        syncStatus = SyncStatus.CREATED
    )
    @Test
    fun search_filters_warranties() = runTest {
        val warranties = listOf(
            createWarranty("Laptop", 100),
            createWarranty("TV", 200)
        )
        coEvery { getAllUseCase() } returns flowOf(warranties)
        val vm = WarrantyViewModel(
            getAllUseCase,
            deleteUseCase
        )
        vm.onEvent(WarrantyEvent.SearchChanged("lap"))
        advanceTimeBy(400)
        assertEquals(
            1,
            vm.uiState.value.warranties.size
        )
        assertEquals(
            "Laptop",
            vm.uiState.value.warranties.first().title
        )
    }
    @Test
    fun toggle_sort_changes_order() = runTest {
        val warranties = listOf(
            createWarranty("Old", 100),
            createWarranty("New", 200)
        )
        coEvery { getAllUseCase() } returns flowOf(warranties)
        val vm = WarrantyViewModel(
            getAllUseCase,
            deleteUseCase
        )
        vm.onEvent(WarrantyEvent.ToggleSortOrder)
        advanceUntilIdle()
        assertEquals(
            "New",
            vm.uiState.value.warranties.first().title
        )
    }
    @Test
    fun delete_event_calls_usecase() = runTest {
        val item = createWarranty("Laptop", 100)
        coEvery { getAllUseCase() } returns flowOf(emptyList())
        val vm = WarrantyViewModel(
            getAllUseCase,
            deleteUseCase
        )
        vm.onEvent(
            WarrantyEvent.DeleteWarranty(item)
        )
        advanceUntilIdle()
        coVerify { deleteUseCase(item) }
    }
    @Test
    fun search_not_found_returns_empty_list() = runTest {
        val warranties = listOf(
            createWarranty("Laptop", 100),
            createWarranty("TV", 200)
        )
        coEvery { getAllUseCase() } returns flowOf(warranties)
        val vm = WarrantyViewModel(
            getAllUseCase,
            deleteUseCase
        )
        vm.onEvent(WarrantyEvent.SearchChanged("xxxxx"))
        advanceTimeBy(400)
        assertTrue(vm.uiState.value.warranties.isEmpty())
    }
}