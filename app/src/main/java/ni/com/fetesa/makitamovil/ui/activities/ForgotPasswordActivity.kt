package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.IForgotPasswordPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ForgotPasswordPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IForgotPasswordView
import ni.com.fetesa.makitamovil.utils.toast

class ForgotPasswordActivity : BaseActivity(), IForgotPasswordView {

    private lateinit var mTxtUser: EditText
    private lateinit var mBtnVerify: Button
    private lateinit var mForgotPasswordPresenter: IForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        mTxtUser = findViewById(R.id.edit_text_user)
        mBtnVerify = findViewById(R.id.button_verify_email)

        mForgotPasswordPresenter = ForgotPasswordPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mBtnVerify.setOnClickListener {
            if(mTxtUser.text.toString() != ""){
                mForgotPasswordPresenter.verifyUser(mTxtUser.text.toString())
            }
            else{
                showEmptyFields()
            }
        }

        supportActionBar!!.title = "Olvidé mi contraseña"

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

    override fun showValidationProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_validating_user))
    }

    override fun hideValidationProgress() {
        this.hideProgressDialog()
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun showEmptyFields() {
        this.toast(R.string.generic_empty_fields)
    }

    override fun navigateToVerificationCode() {
        val intent = Intent(this, VerificationCodeForgotPasswordActivity::class.java)
        intent.putExtra("user", mTxtUser.text.toString())
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
