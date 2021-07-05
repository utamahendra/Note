package com.example.notes.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.local.dao.NoteDao
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.data.repository.NoteEditorRepository
import com.example.notes.domain.source.NoteEditorDataSource
import junit.framework.Assert
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
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NoteEditorRepositoryTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository by inject<NoteEditorDataSource>()

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
                    NoteEditorRepository(noteDatabase) as NoteEditorDataSource
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
            val noteEntity = NoteEntity(0, "title", "content")

            BDDMockito.given(noteDatabase.noteDao()).willReturn(noteDao)
            BDDMockito.given(noteDao.insertNote(noteEntity)).willReturn(Unit)
            repository.saveNote("title", "content")

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).insertNote(noteEntity)
        }
    }

    @Test
    fun `delete note success`() {
        testDispatcher.runBlockingTest {

            BDDMockito.given(noteDatabase.noteDao()).willReturn(noteDao)
            BDDMockito.given(noteDao.deleteNote(1)).willReturn(Unit)
            repository.deleteNote(1)

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).deleteNote(1)
        }
    }

    @Test
    fun `get note detail success`() {
        testDispatcher.runBlockingTest {
            val expected = NoteEntity(1, "title", "content")

            BDDMockito.given(noteDatabase.noteDao()).willReturn(noteDao)
            BDDMockito.given(noteDao.getNoteDetail(1)).willReturn(expected)
            val actual = repository.getNote(1)

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).getNoteDetail(1)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `update note success`() {
        testDispatcher.runBlockingTest {
            val request = NoteEntity(1, "title", "content")

            BDDMockito.given(noteDatabase.noteDao()).willReturn(noteDao)
            BDDMockito.given(noteDao.updateNote(request)).willReturn(Unit)
            repository.updateNote(1, "title", "content")

            Mockito.verify(noteDatabase).noteDao()
            Mockito.verify(noteDao).updateNote(request)
        }
    }
}
