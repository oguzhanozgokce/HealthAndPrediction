package com.oguzhanozgokce.healthandprediction.constants

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Locale

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }
}

fun Fragment.showLocationConfirmationPopup(
    onConfirm1: Context,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val alertDialogBuilder = AlertDialog.Builder(requireContext())
    alertDialogBuilder.setTitle("Konum Onayı")
    alertDialogBuilder.setMessage("Konumunuzu onaylamak istiyor musunuz?")
    alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->
        onConfirm.invoke()
    }
    alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->
        onCancel.invoke()
    }
    alertDialogBuilder.show()
}

fun Fragment.showLocationPermissionDeniedToast(requireContext: Context) {
    Toast.makeText(
        requireContext(),
        "Konum izni reddedildi, manuel konum seçimi yapabilirsiniz.",
        Toast.LENGTH_SHORT
    ).show()
}

