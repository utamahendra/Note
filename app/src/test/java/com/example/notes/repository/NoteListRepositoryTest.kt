package com.example.notes.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.local.dao.NoteDao
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.data.repository.NoteListRepository
import com.example.notes.domain.source.NoteListDataSource
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NoteListRepositoryTest: KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository by inject<NoteListDataSource>()

    @Mock
    private lateinit var noteDatabase: NoteDatabase

    @Mock
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    NoteListRepository(noteDatabase) as NoteListDataSource
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
    fun `get note success return value`() {
        testDispatcher.runBlockingTest {
            val noteEntity = NoteEntity(1, "title", "content")
            val expected: MutableList<NoteEntity> = ArrayList()
            expected.add(noteEntity)

            given(noteDatabase.noteDao()).willReturn(noteDao)
            given(noteDao.getNotes()).willReturn(expected)
            val actual = repository.getNotes()

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).getNotes()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `get note success return null`() {
        testDispatcher.runBlockingTest {
            given(noteDatabase.noteDao()).willReturn(noteDao)
            given(noteDao.getNotes()).willReturn(null)
            val actual = repository.getNotes()

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).getNotes()
            assertEquals(null, actual)
        }
    }
}