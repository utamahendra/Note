package com.example.notes.common.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.notes.common.extension.slideOutRightTransition

abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentExtras()
        initView()
        initListener()
        starObserver()
    }

    abstract fun initView()

    abstract fun initListener()

    open fun starObserver(){}

    open fun getIntentExtras(){}

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutRightTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> false
        }
    }

    open fun setupToolbarAndBackButton(toolbar: Toolbar, title: String? = null) {
        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(title != null)
                this.title = title
            }
        }
    }
}