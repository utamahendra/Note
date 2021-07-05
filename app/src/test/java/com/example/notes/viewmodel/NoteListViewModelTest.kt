package com.example.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.common.Either
import com.example.notes.common.getTestObserver
import com.example.notes.common.viewstate.ListingViewState
import com.example.notes.common.viewstate.ViewError
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.usecase.NoteListUseCase
import com.example.notes.presentation.notelist.NoteListViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NoteListViewModelTest: KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val viewModel by inject<NoteListViewModel>()

    @Mock
    private lateinit var useCase: NoteListUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    NoteListViewModel(useCase)
                }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `get notes success`() {
        testDispatcher.runBlockingTest {
            val noteEntity = NoteEntity(1, "title", "content")
            val result: MutableList<NoteEntity> = ArrayList()
            result.add(noteEntity)

            val expected = listOf<ListingViewState<NoteEntity>>(
                ListingViewState.Success(result)
            )

            given(useCase.invoke(Unit)).willReturn(Either.Success(result))

            val actual = viewModel.noteListState.getTestObserver()

            viewModel.getNotes()

            assertEquals(expected, actual.observedValues)
        }
    }

    @Test
    fun `get notes failed`() {
        testDispatcher.runBlockingTest {
            val expected = listOf<ListingViewState<NoteEntity>>(
                ListingViewState.Empty()
            )

            given(useCase.invoke(Unit)).willReturn(Either.Fail(ViewError("empty")))

            val actual = viewModel.noteListState.getTestObserver()

            viewModel.getNotes()

            assertEquals(expected, actual.observedValues)
        }
    }
}