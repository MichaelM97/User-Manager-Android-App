package com.michaelmccormick.usermanager.core.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(
    @StringRes stringResId: Int,
    length: Int = Toast.LENGTH_SHORT,
) = Toast.makeText(this, stringResId, length).show()
