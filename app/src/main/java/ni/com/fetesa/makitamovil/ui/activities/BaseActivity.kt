package ni.com.fetesa.makitamovil.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import android.app.ProgressDialog
import android.content.DialogInterface
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.ui.views.IBaseView


/**
 * Created by kevin on 22/2/2018.
 */
abstract class BaseActivity: AppCompatActivity(), IBaseView {

    protected var mProgressDialog: ProgressDialog? = null
    override fun showProgressDialog(msg:String) {
        //Show progress dialog here
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
        }
        mProgressDialog?.setMessage(msg)
        mProgressDialog?.isIndeterminate = true
        mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.show()


    }

    override fun hideProgressDialog() {
        //Logic to hide progress dialog
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
            mProgressDialog = null
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        //Activity setup orientation and toolbar
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    }

    protected fun showConfirmDialog(titleResId: Int, iconResId: Int, messageResId: Int,
                                    positiveBtnResId: Int, negativeBtnResId: Int,
                                    positiveListener: DialogInterface.OnClickListener,
                                    negativeListener: DialogInterface.OnClickListener
                                    ){
        AlertDialog.Builder(this)
                .setIcon(iconResId)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(positiveBtnResId, positiveListener)
                .setNegativeButton(negativeBtnResId, negativeListener)
                .create()
                .show()
    }
}