package com.example.notes.domain.usecase

import com.example.notes.common.Either
import com.example.notes.common.viewstate.ViewError
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.domain.source.NoteListDataSource

class NoteListUseCaseImpl(private val repository: NoteListDataSource) : NoteListUseCase {

    override suspend fun invoke(params: Unit): Either<List<NoteEntity>, ViewError> {
        val result = repository.getNotes()
        return if (result.isNullOrEmpty()) {
            Either.Fail(ViewError("EMPTY"))
        } else {
            Either.Success(result)
        }
    }
}