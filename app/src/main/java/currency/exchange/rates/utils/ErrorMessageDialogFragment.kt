package currency.exchange.rates.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.error_fragment.*
import currency.exchange.rates.R

/**
 * Dialog message for showing errors
 * use as ErrorMessageDialogFragment.buildDialog(
 *      @param title - string of title
 *      @param message - string of message
 *      @param icom - set icon
 *      @param click - action that will be invoked on click
 *   ).show(
 *      @param fragment manager - supportFragmentManager or fragmentManager
 *      @param tag - tag for fragment)
 */
class ErrorMessageDialogFragment : AppCompatDialogFragment() {

    private var buttonClick: (DialogFragment) -> Unit = { _ -> dismiss() }

    companion object {
        private const val TITLE = "TITLE"
        private const val MESSAGE = "MESSAGE"
        private const val ICON = "ICON"

        fun buildDialog(
                title: String?,
                message: String,
                @DrawableRes icon: Int = R.drawable.alert_circle_outline,
                click: (DialogFragment) -> Unit): ErrorMessageDialogFragment {

            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(MESSAGE, message)
            bundle.putInt(ICON, icon)
            val df = ErrorMessageDialogFragment()
            df.buttonClick = click
            df.arguments = bundle
            return df
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity as Context) {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            }

            override fun onBackPressed() {
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.error_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryButton.setOnClickListener { buttonClick(this) }
        title.text = arguments?.getString(TITLE)
        message.text = arguments?.getString(MESSAGE)
        icon.setImageDrawable(VectorDrawableCompat.create(resources, (arguments?.getInt(ICON)
                ?: R.drawable.alert_circle_outline), null))
    }
}