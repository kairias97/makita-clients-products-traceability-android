
package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.IVerificationCodeForgotPasswordPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.VerificationCodeForgotPasswordPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IVerificationCodeForgotPasswordView
import ni.com.fetesa.makitamovil.utils.toast

class VerificationCodeForgotPasswordActivity : BaseActivity(), IVerificationCodeForgotPasswordView {

    private lateinit var mUser: String
    private lateinit var mTxtCode: EditText
    private lateinit var mBtnVerify: Button

    private lateinit var mVerificationCodeForgotPasswordPresenter: IVerificationCodeForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code_forgot_password)

        if(intent.extras != null){
            mUser = intent.getStringExtra("user")
        }

        mTxtCode = findViewById(R.id.edit_text_user)
        mBtnVerify = findViewById(R.id.button_verify_email)

        mVerificationCodeForgotPasswordPresenter = VerificationCodeForgotPasswordPresenterImpl(this, MakitaRemoteDataSource.instance,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ))

        mBtnVerify.setOnClickListener {
            if(mTxtCode.text.toString() != ""){
                mVerificationCodeForgotPasswordPresenter.validateCode(mUser, mTxtCode.text.toString())
            }
            else{
                this.showEmptyFields()
            }
        }

        supportActionBar!!.title = "Verificación de código"

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
        finish()
    }

    override fun onBackPressed() {
        returnTop()
    }

    override fun showVerificationProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_forgot_password_verification_code))
    }

    override fun hideVerificationProgress() {
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

    override fun navigateToResetPasswordCompletion(requestID: String) {
        val intent = Intent(this, CompletionForgotPasswordActivity::class.java)
        intent.putExtra("user", mUser)
        intent.putExtra("code", mTxtCode.text.toString())
        intent.putExtra("requestID", requestID)
        startActivity(intent)
    }

    override fun showEmptyFields() {
        this.toast(R.string.generic_empty_fields)
    }
}
