package com.example.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.common.Either
import com.example.notes.common.ValidateForm.TITLE_EMPTY
import com.example.notes.common.getTestObserver
import com.example.notes.common.viewstate.ViewError
import com.example.notes.common.viewstate.ViewState
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.model.NoteEditorParams
import com.example.notes.domain.usecase.NoteEditorUseCase
import com.example.notes.presentation.noteeditor.NoteEditorViewModel
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

class NoteEditorViewModelTest: KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val viewModel by inject<NoteEditorViewModel>()

    @Mock
    private lateinit var useCase: NoteEditorUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    NoteEditorViewModel(useCase)
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
    fun `save note success`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "title", "content")
            val expected = listOf<ViewState<Unit>>(ViewState.Success(Unit))

            given(useCase.invoke(params)).willReturn(Either.Success(Unit))

            val actual = viewModel.saveNoteState.getTestObserver()
            viewModel.saveNote(1, "title", "content")

            assertEquals(expected, actual.observedValues)
        }
    }

    @Test
    fun `save note failed`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "title", "content")
            val expected = listOf<ViewState<Unit>>(ViewState.Error(ViewError(TITLE_EMPTY)))

            given(useCase.invoke(params)).willReturn(Either.Fail(ViewError(TITLE_EMPTY)))

            val actual = viewModel.saveNoteState.getTestObserver()
            viewModel.saveNote(1, "title", "content")

            assertEquals(expected, actual.observedValues)
        }
    }

    @Test
    fun `delete note success`() {
        testDispatcher.runBlockingTest {
            val expected = listOf(Unit)

            given(useCase.deleteNote(1)).willReturn(Unit)

            val actual = viewModel.deleteNoteState.getTestObserver()
            viewModel.deleteNote(1)

            assertEquals(expected, actual.observedValues)
        }
    }

    @Test
    fun `get note detail success`() {
        testDispatcher.runBlockingTest {
            val result = NoteEntity(1, "title", "content")
            val expected = listOf(result)

            given(useCase.getNote(1)).willReturn(result)

            val actual = viewModel.getNoteState.getTestObserver()
            viewModel.getNote(1)

            assertEquals(expected, actual.observedValues)
        }
    }
}