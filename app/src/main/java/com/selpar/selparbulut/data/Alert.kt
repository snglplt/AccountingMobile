package com.selpar.selparbulut.data

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.selpar.selparbulut.R

class Alert {
    fun showAlert(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        // Add a button to dismiss the dialog
        builder.setPositiveButton(context.getString(R.string.tamam)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}