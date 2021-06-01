package com.example.moviedbjm

import android.view.View

fun View.visibleOrGone(isVisible: Boolean): View {
    visibility = if (isVisible) View.VISIBLE else View.GONE
    return this
}