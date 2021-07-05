package com.example.notes.presentation.noteeditor

import android.os.Bundle
import android.text.Html
import com.example.notes.common.ValidateForm.CONTENT_EMPTY
import com.example.notes.common.ValidateForm.FORM_EMPTY
import com.example.notes.common.ValidateForm.TITLE_EMPTY
import com.example.notes.common.base.BaseActivity
import com.example.notes.common.extension.*
import com.example.notes.common.viewstate.ViewState
import com.example.notes.data.local.entity.NoteEntity
import com.example.notes.databinding.ActivityNoteEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteEditorActivity : BaseActivity() {

    companion object {
        const val REQUEST_NOTE = 200
        const val EXTRAS_ID = "id";
    }

    private val binding by viewBinding(ActivityNoteEditorBinding::inflate)
    private val viewModel by viewModel<NoteEditorViewModel>()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (id != 0) getNote(id)
    }

    override fun getIntentExtras() {
        id = intent.getIntExtra(EXTRAS_ID, 0)
    }

    override fun initView() {
        setupToolbarAndBackButton(binding.toolbar, "Note Editor")
        binding.btnDelete.isEnabled = id != 0
    }

    override fun initListener() {
        with(binding) {
            etTitle.afterTextChanged { tilTitle.error = null }

            etContent.afterTextChanged { tvError.gone() }

            btnSave.setOnClickListener { saveNote() }

            btnDelete.setOnClickListener { deleteNote() }

        }
    }

    override fun starObserver() {
        viewModel.getNoteState.observe(this) { bindData(it) }
        viewModel.deleteNoteState.observe(this) { navigateToNoteList() }
        viewModel.saveNoteState.observe(this) { state ->
            when (state) {
                is ViewState.Error -> showErrorForm(state.viewError?.errorCode.orEmpty())
                is ViewState.Success -> navigateToNoteList()
            }
        }
    }

    private fun showErrorForm(errorCode: String) {
        when (errorCode) {
            TITLE_EMPTY -> binding.tilTitle.error = "must be filled"
            CONTENT_EMPTY -> binding.tvError.visible()
            FORM_EMPTY -> {
                binding.tilTitle.error = "must be filled"
                binding.tvError.visible()
            }
        }
    }

    private fun bindData(data: NoteEntity) {
        with(binding) {
            etTitle.setText(data.title)
            etContent.setText(Html.fromHtml(data.content))
        }
    }

    private fun getNote(id: Int) {
        viewModel.getNote(id)
    }

    private fun saveNote() {
        viewModel.saveNote(id, binding.etTitle.text.toString(), binding.etContent.text.toString())
    }

    private fun deleteNote() {
        viewModel.deleteNote(id)
    }

    private fun navigateToNoteList() {
        setResult(RESULT_OK)
        finish()
        slideOutRightTransition()
    }
}