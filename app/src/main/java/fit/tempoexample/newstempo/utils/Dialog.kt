package fit.tempoexample.newstempo.utils

import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import fit.tempoexample.newstempo.R
import fit.tempoexample.newstempo.databinding.ErrorDialogBinding
import fit.tempoexample.newstempo.databinding.LoadingDialogBinding


/**
 * Created by Mohammed Farid on 8/15/2021
 * Contact me : m.farid.shawky@gmail.com
 */
object Dialog {

    fun loadingDialog(context: Context): AlertDialog {
        val dialogBinding: LoadingDialogBinding =
            LoadingDialogBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context, R.style.WrapContentDialog).create()
        if (alertDialog.isShowing) {
            alertDialog.cancel()
        } else {
            alertDialog.apply {
                setView(dialogBinding.root)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
                window?.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window?.setGravity(Gravity.CENTER)
            }
        }
        return alertDialog
    }

    fun errorDialog(context: Context, msg: String): AlertDialog {
        val dialogBinding: ErrorDialogBinding =
            ErrorDialogBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context, R.style.WrapContentDialog).create()
        dialogBinding.tvMsg.text = msg
        if (alertDialog.isShowing) {
            alertDialog.cancel()
        } else {
            alertDialog.apply {
                setView(dialogBinding.root)
                setCancelable(true)
                window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
                window?.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window?.setGravity(Gravity.CENTER)
            }.show()
        }
        return alertDialog
    }
}