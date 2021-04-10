package com.satryaway.bcgdvtest.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

class CommonUtils {
    companion object {
        fun hideSoftKeyboard(activity: Activity, et: View) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(et.windowToken, 0)
        }
    }
}