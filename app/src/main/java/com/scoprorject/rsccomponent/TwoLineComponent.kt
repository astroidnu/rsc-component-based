package com.scoprorject.rsccomponent

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_two_line.view.*

/**
 * Created by ibnumuzzakkir on 22,October,2019
 */

class TwoLineComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        setupProperty(context,attrs)
    }

    private fun setupProperty(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.item_two_line, this, true)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CommonField,
            0, 0
        )
        settingProperty(typedArray)
    }

    private fun settingProperty(typedArray: TypedArray) {
        val title = typedArray.getString(R.styleable.CommonField_title)
        val subTitle = typedArray.getString(R.styleable.CommonField_subtitle)
        tv_title.text = title
        tv_subtitle.text = subTitle
    }

}