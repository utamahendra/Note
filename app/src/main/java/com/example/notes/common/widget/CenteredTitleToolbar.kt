package com.example.notes.common.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.notes.R
import com.example.notes.common.extension.getScreenWidth

class CenteredTitleToolbar(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttributeSet: Int = 0
) : Toolbar(context, attributeSet, defStyleAttributeSet) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private var screenWidth: Int = getScreenWidth()
    private var titleTextView: TextView = TextView(context)

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CenteredTitleToolbar,
            0,
            0
        )
        val titleTextAppearance = typedArray.getResourceId(
            R.styleable.CenteredTitleToolbar_titleStyle,
            R.style.TextAppearance_AppCompat_Body1
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleTextView.setTextAppearance(titleTextAppearance)
        } else {
            titleTextView.setTextAppearance(context, titleTextAppearance)
        }

        addView(titleTextView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val location = IntArray(2)
        titleTextView.getLocationOnScreen(location)
        titleTextView.translationX =
            titleTextView.translationX + (-location.first() + screenWidth / 2 - titleTextView.width / 2)
    }

    override fun setTitle(resId: Int) {
        titleTextView.setText(resId)
        requestLayout()
    }

    override fun setTitle(title: CharSequence?) {
        titleTextView.text = title
        requestLayout()
    }
}