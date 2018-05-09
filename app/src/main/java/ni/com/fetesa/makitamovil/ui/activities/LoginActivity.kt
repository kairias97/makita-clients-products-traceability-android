package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.ILoginPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.LoginPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.ILoginView
import ni.com.fetesa.makitamovil.utils.toast

class LoginActivity : BaseActivity(), ILoginView {
    private lateinit var mRegisterTextView: TextView
    private lateinit var mForgotPasswordTextView: TextView
    private lateinit var mButtonLogin: Button
    private lateinit var mTextEmail: EditText
    private lateinit var mTextPassword: EditText

    private lateinit var mLoginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()

        mRegisterTextView = findViewById<TextView>(R.id.textView_register)
        mForgotPasswordTextView = findViewById<TextView>(R.id.textView_forgot_password)
        mButtonLogin = findViewById<Button>(R.id.button_login)
        mTextEmail = findViewById(R.id.edit_text_email)
        mTextPassword = findViewById(R.id.edit_text_password)

        mRegisterTextView.setOnClickListener {
            val intentTarget = Intent(this, RegisterActivity::class.java)
            startActivity(intentTarget)
        }
        mForgotPasswordTextView.setOnClickListener {
            val intentTarget = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentTarget)
        }

        mLoginPresenter = LoginPresenterImpl(SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ),this, MakitaRemoteDataSource.instance)

        mButtonLogin.setOnClickListener {
            val email = mTextEmail.text.toString()
            val password = mTextPassword.text.toString()
            if(email != "" && password != ""){
                mLoginPresenter.validateCredentials(email,password)
            }
            else{
                this.toast(getString(R.string.error_login_empty_fields))
            }
        }
    }

    override fun showValidatingCredentials() {
        this.showProgressDialog(getString(R.string.progress_dialog_login_validating))
    }

    override fun showCustomError(error: String) {
        this.toast(error)
    }

    override fun showError() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun hideValidatingCredentials() {
        this.hideProgressDialog()
    }

    override fun prepareToNavigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
