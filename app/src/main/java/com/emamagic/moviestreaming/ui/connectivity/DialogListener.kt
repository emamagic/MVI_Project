package com.emamagic.moviestreaming.ui.connectivity

import androidx.fragment.app.DialogFragment

interface DialogListener {
    fun onAccept(dialog: DialogFragment?)
    fun onDecline(dialog: DialogFragment?)
}