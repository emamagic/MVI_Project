package com.emamagic.moviestreaming.provider.conectivity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.emamagic.moviestreaming.R

class ConnectivityFragment: DialogFragment(R.layout.layout_dialog_connection) {

    private var message: String = ""
    private var accept: String? = null
    private var decline: String? = null
    private var type: Int = 1
    private var canBeDismiss: Boolean = true
    private var action: DialogListener? = null

    companion object {
        fun newInstance(
            message: String,
            accept: String?,
            decline: String?,
            @AlarmType type: Int,
            canBeDismiss: Boolean = true
        ): ConnectivityFragment {
            val args = Bundle()
            args.putString("message" ,message)
            args.putString("accept" ,accept)
            args.putString("decline" ,decline)
            args.putInt("type" ,type)
            args.putBoolean("canBeDismiss" ,canBeDismiss)
            val fragment = ConnectivityFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString("message") ?: ""
        accept = arguments?.getString("accept")
        decline = arguments?.getString("decline")
        type = arguments?.getInt("type") ?: 1
        canBeDismiss = arguments?.getBoolean("canBeDismiss") ?: true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogListener) {
            action = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val msg = view.findViewById<TextView>(R.id.alert_message)
        val success = view.findViewById<Button>(R.id.alert_done)
        val cancel = view.findViewById<Button>(R.id.alert_cancel)
        val alertTitle = view.findViewById<TextView>(R.id.alert_title)
        val dismiss = view.findViewById<ImageButton>(R.id.btnDismiss)
        when (type) {
            AlarmType.INFO -> {
                alertTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                alertTitle.text = "Info"
                alertTitle.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_error_blue_24dp,
                    0
                )
            }
            AlarmType.WARNING -> {
                alertTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.warning))
                alertTitle.text = "Warning"
                alertTitle.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_error_yellow_24dp,
                    0
                )
            }
            AlarmType.ERROR -> {
                alertTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                alertTitle.text = "Error"
                alertTitle.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_error_red_24dp,
                    0
                )
            }
        }
        msg.text = message
        if (accept == null && decline == null) {
            cancel.visibility = View.GONE
            success.text = "تایید"
        } else {
            if (accept == null) {
                success.visibility = View.GONE
            } else {
                success.text = accept
            }
            if (decline == null) {
                cancel.visibility = View.GONE
            } else {
                cancel.text = decline
            }
        }
        if (canBeDismiss) {
            dismiss.visibility = View.VISIBLE
        } else {
            dismiss.visibility = View.GONE
        }
        if (action == null) {
            cancel.setOnClickListener { action?.onDecline(this) }
            success.setOnClickListener { action?.onAccept(this) }
            dismiss.setOnClickListener { dismiss() }
        } else {
            cancel.setOnClickListener { action?.onDecline(this) }
            success.setOnClickListener { action?.onAccept(this) }
            dismiss.setOnClickListener {  dismiss() }
        }
    }

}