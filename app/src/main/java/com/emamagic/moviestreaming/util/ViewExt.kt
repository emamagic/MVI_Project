package com.emamagic.moviestreaming.util

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.emamagic.moviestreaming.R

inline fun EditText.onTextChange(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }
    })
}

inline fun SearchView.onQueryTextListener(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })

}

inline fun SearchView.onQueryTextSubmitListener(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            listener(query.orEmpty())
            return true
        }

        override fun onQueryTextChange(newText: String?) = true
    })
}


fun Fragment.toasty(title: String ,@ToastyMode selectedMode: Int? = null) {
    val layout = layoutInflater.inflate(
        R.layout.toast_layout,
        requireView().findViewById(R.id.toast_root)
    )
    when (selectedMode) {

        ToastyMode.MODE_TOAST_SUCCESS -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_corroct_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_corroct_toast)
        }
        ToastyMode.MODE_TOAST_WARNING -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_warning_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_warning_toast)
            layout.findViewById<TextView>(R.id.toast_txt)
                .setTextColor(resources.getColor(R.color.black))
        }
        ToastyMode.MODE_TOAST_ERROR -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_error_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_error_toast)
        }
        else -> {
            Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
        }

    }

    layout.findViewById<TextView>(R.id.toast_txt).text = title
    if (selectedMode != null) {
        Toast(requireActivity()).apply {
            setGravity(Gravity.BOTTOM, 0, 100)
            duration = Toast.LENGTH_LONG
            view = layout
        }.show()
    }
}


fun Fragment.toasty(@StringRes titleId: Int ,@ToastyMode selectedMode: Int? = null) {
    val layout = layoutInflater.inflate(
        R.layout.toast_layout,
        requireView().findViewById(R.id.toast_root)
    )
    when (selectedMode) {

        ToastyMode.MODE_TOAST_SUCCESS -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_corroct_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_corroct_toast)
        }
        ToastyMode.MODE_TOAST_WARNING -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_warning_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_warning_toast)
            layout.findViewById<TextView>(R.id.toast_txt)
                .setTextColor(resources.getColor(R.color.black))
        }
        ToastyMode.MODE_TOAST_ERROR -> {
            layout.findViewById<ImageView>(R.id.toast_img)
                .setImageResource(R.drawable.ic_error_toast)
            layout.findViewById<ConstraintLayout>(R.id.toast_root)
                .setBackgroundResource(R.drawable.bg_error_toast)
        }
        else -> {
            Toast.makeText(requireContext(), resources.getString(titleId), Toast.LENGTH_LONG).show()
        }

    }

    layout.findViewById<TextView>(R.id.toast_txt).text = resources.getString(titleId)
    if (selectedMode != null) {
        Toast(requireActivity()).apply {
            setGravity(Gravity.BOTTOM, 0, 100)
            duration = Toast.LENGTH_LONG
            view = layout
        }.show()
    }
}

val <T> T.exhaustive: T
    get() = this
