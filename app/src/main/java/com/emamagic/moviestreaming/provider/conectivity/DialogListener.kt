package com.emamagic.moviestreaming.provider.conectivity

import androidx.fragment.app.DialogFragment

interface DialogListener {
    fun onAccept(dialog: DialogFragment?)
    fun onDecline(dialog: DialogFragment?)
}