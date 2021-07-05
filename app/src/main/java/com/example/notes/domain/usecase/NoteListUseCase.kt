package com.example.notes.domain.usecase

import com.example.notes.common.UseCase
import com.example.notes.data.local.entity.NoteEntity

interface NoteListUseCase: UseCase<Unit, List<NoteEntity>>