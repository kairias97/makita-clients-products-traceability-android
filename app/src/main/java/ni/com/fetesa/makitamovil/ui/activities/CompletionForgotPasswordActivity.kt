package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.ICompletionForgotPasswordPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.CompletionForgotPasswordPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.ICompletionForgoPasswordView
import ni.com.fetesa.makitamovil.utils.toast

class CompletionForgotPasswordActivity : BaseActivity(), ICompletionForgoPasswordView {

    private lateinit var mUser: String
    private lateinit var mCode: String
    private lateinit var mRequestID: String

    private lateinit var mTxtNewPassword: EditText
    private lateinit var mBtnEnd: Button

    private lateinit var mCompletionForgotPasswordPresenter: ICompletionForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completion_forgot_password)

        if(intent.extras != null){
            mUser = intent.getStringExtra("user")
            mCode = intent.getStringExtra("code")
            mRequestID = intent.getStringExtra("requestID")
        }

        mTxtNewPassword = findViewById(R.id.edit_text_user)
        mBtnEnd = findViewById(R.id.button_verify_email)

        mCompletionForgotPasswordPresenter = CompletionForgotPasswordPresenterImpl(this, MakitaRemoteDataSource.instance,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ))

        mBtnEnd.setOnClickListener {
            if(mTxtNewPassword.text.toString() != ""){
                mCompletionForgotPasswordPresenter.resetPasswordCompletion(mUser, mCode, mRequestID, mTxtNewPassword.text.toString())
            }
            else{
                this.showEmptyFields()
            }
        }

        supportActionBar!!.title = "Restauración de contraseña"

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }
    private fun returnTop(){
        //finish()
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onBackPressed() {
        returnTop()
    }

    override fun showSavingPasswordProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_competion_forgot_password))
    }

    override fun hideSavingkPasswordProgress() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showEmptyFields() {
        this.toast(R.string.generic_empty_fields)
    }
}
