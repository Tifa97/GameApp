package com.example.gameapp.util

import android.content.Context
import android.widget.Toast

fun showToast(text: String, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}