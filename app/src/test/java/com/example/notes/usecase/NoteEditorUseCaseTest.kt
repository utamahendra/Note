package com.example.notes.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.common.Either
import com.example.notes.common.ValidateForm.CONTENT_EMPTY
import com.example.notes.common.ValidateForm.FORM_EMPTY
import com.example.notes.common.ValidateForm.FORM_VALID
import com.example.notes.common.ValidateForm.TITLE_EMPTY
import com.example.notes.common.viewstate.ViewError
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.model.NoteEditorParams
import com.example.notes.domain.source.NoteEditorDataSource
import com.example.notes.domain.source.NoteListDataSource
import com.example.notes.domain.usecase.NoteEditorUseCase
import com.example.notes.domain.usecase.NoteEditorUseCaseImpl
import com.example.notes.domain.usecase.NoteListUseCase
import com.example.notes.domain.usecase.NoteListUseCaseImpl
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

class NoteEditorUseCaseTest: KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val noteEditorUseCase by inject<NoteEditorUseCase>()

    @Mock
    private lateinit var repository: NoteEditorDataSource


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    NoteEditorUseCaseImpl(repository) as NoteEditorUseCase
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
            val params = NoteEditorParams(0, "title", "content")
            val expected = Either.Success(Unit)

            given(noteEditorUseCase.invoke(params)).willReturn(expected)
            given(repository.saveNote("title", "content")).willReturn(Unit)

            val actual = noteEditorUseCase.invoke(params)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `update note success`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "title", "content")
            val expected = Either.Success(Unit)

            given(noteEditorUseCase.invoke(params)).willReturn(expected)
            given(repository.updateNote(1, "title", "content")).willReturn(Unit)

            val actual = noteEditorUseCase.invoke(params)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get note detail success`() {
        testDispatcher.runBlockingTest {
            val expected = NoteEntity(1, "title", "content")

            given(noteEditorUseCase.getNote(1)).willReturn(expected)
            given(repository.getNote(1)).willReturn(expected)

            val actual = noteEditorUseCase.getNote(1)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `delete note success`() {
        testDispatcher.runBlockingTest {
            val expected = Unit

            given(noteEditorUseCase.deleteNote(1)).willReturn(expected)
            given(repository.deleteNote(1)).willReturn(expected)

            val actual = noteEditorUseCase.deleteNote(1)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `validate form empty`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "", "")
            val expected = Either.Fail(ViewError(FORM_EMPTY))

            val actual = noteEditorUseCase.invoke(params)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `validate form title empty`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "", "content")
            val expected = Either.Fail(ViewError(TITLE_EMPTY))

            val actual = noteEditorUseCase.invoke(params)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `validate form content empty`() {
        testDispatcher.runBlockingTest {
            val params = NoteEditorParams(1, "title", "")
            val expected = Either.Fail(ViewError(CONTENT_EMPTY))

            val actual = noteEditorUseCase.invoke(params)
            assertEquals(expected, actual)
        }
    }
}