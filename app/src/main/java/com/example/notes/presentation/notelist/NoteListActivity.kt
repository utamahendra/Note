package com.example.notes.presentation.notelist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.notes.common.base.BaseActivity
import com.example.notes.common.extension.startActivityForResultLeftTransition
import com.example.notes.common.extension.viewBinding
import com.example.notes.common.viewstate.ListingViewState
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.databinding.ActivityNoteListBinding
import com.example.notes.presentation.noteeditor.NoteEditorActivity
import com.example.notes.presentation.noteeditor.NoteEditorActivity.Companion.EXTRAS_ID
import com.example.notes.presentation.noteeditor.NoteEditorActivity.Companion.REQUEST_NOTE
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteListActivity : BaseActivity() {

    private val viewModel by viewModel<NoteListViewModel>()
    private val binding by viewBinding(ActivityNoteListBinding::inflate)
    private val adapter by lazy { NoteListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getNotes()
    }

    override fun initView() {
        setupToolbarAndBackButton(binding.toolbar, "Notes")
        binding.rvNotes.adapter = adapter
    }

    override fun initListener() {
        binding.fabAddNote.setOnClickListener { navigateToNoteEditor() }

        adapter.setItemClickListener { _, noteEntity, _ ->
            noteEntity?.let { navigateToNoteEditor(it.id) }
        }

        binding.srlNotes.setOnRefreshListener {
            binding.srlNotes.isRefreshing = false
            getNotes()
        }
    }

    override fun starObserver() {
        viewModel.noteListState.observe(this) { state ->
            when (state) {
                is ListingViewState.Success -> showNotes(state.data)

                is ListingViewState.Empty -> showEmptyNotes()
            }
        }
    }

    private fun getNotes() {
        adapter.clear()
        viewModel.getNotes()
    }

    private fun showNotes(data: List<NoteEntity>) {
        adapter.addAll(data as ArrayList<NoteEntity>)
    }

    private fun showEmptyNotes() {
        Toast.makeText(this, "Notes Empty", Toast.LENGTH_LONG).show()
    }

    private fun navigateToNoteEditor(id: Int = 0) {
        startActivityForResultLeftTransition(intentFor<NoteEditorActivity>(EXTRAS_ID to id), REQUEST_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_NOTE && resultCode == RESULT_OK) getNotes()
        super.onActivityResult(requestCode, resultCode, data)

    }
}