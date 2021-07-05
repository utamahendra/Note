package com.example.notes.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.common.Either
import com.example.notes.common.viewstate.ViewError
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.source.NoteListDataSource
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

class NoteListUseCaseTest: KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val noteListUseCase by inject<NoteListUseCase>()

    @Mock
    private lateinit var repository: NoteListDataSource


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    NoteListUseCaseImpl(repository) as NoteListUseCase
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
    fun `get note list success`() {
        testDispatcher.runBlockingTest {
            val noteEntity = NoteEntity(1, "title", "content")
            val notes: MutableList<NoteEntity> = ArrayList()
            notes.add(noteEntity)
            val expected = Either.Success(notes)

            given(repository.getNotes()).willReturn(notes)

            val actual = noteListUseCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get note list empty`() {
        testDispatcher.runBlockingTest {
            val notes: MutableList<NoteEntity> = ArrayList()
            val expected = Either.Fail(ViewError("EMPTY"))

            given(repository.getNotes()).willReturn(notes)

            val actual = noteListUseCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get note list null`() {
        testDispatcher.runBlockingTest {
            val expected = Either.Fail(ViewError("EMPTY"))

            given(repository.getNotes()).willReturn(null)

            val actual = noteListUseCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }
}