package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.ICompletionRegisterPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.CompletionRegisterPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.ICompletionRegisterView
import ni.com.fetesa.makitamovil.utils.toast

class CompletionRegisterActivity : BaseActivity(), ICompletionRegisterView {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtPasswordConfirm: EditText
    private lateinit var btnCompleteRegister: Button
    private lateinit var mCompletionRegisterPresenter: ICompletionRegisterPresenter

    private var typeID: Int = 0
    private lateinit var identificationNumber: String
    private lateinit var registrationID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completion_register)

        supportActionBar!!.title = "Finalizar Registro"

        if(intent.extras != null){
            typeID = intent.getIntExtra("typeID",0)
            identificationNumber = intent.getStringExtra("identificationNumber")
            registrationID = intent.getStringExtra("registrationID")
        }

        txtUser = findViewById(R.id.edit_text_username)
        txtPassword = findViewById(R.id.edit_text_password)
        txtPasswordConfirm = findViewById(R.id.edit_text_password_confirm)
        btnCompleteRegister = findViewById(R.id.button_end_register)

        mCompletionRegisterPresenter = CompletionRegisterPresenterImpl(this, MakitaRemoteDataSource.instance,
                SharedPrefManager(
                        getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                                Context.MODE_PRIVATE)
                ))

        btnCompleteRegister.setOnClickListener {
            val user = txtUser.text.toString()
            val password = txtPassword.text.toString()
            val passwordConfirm = txtPasswordConfirm.text.toString()

            if(user != "" && password != "" && passwordConfirm != ""){
                if(password == passwordConfirm){
                    mCompletionRegisterPresenter.completeRegistration(typeID,identificationNumber,registrationID,user,password)
                }
                else{
                    showPasswordsDontMatchError()
                }
            }
            else{
                showEmptyFieldError()
            }
        }
    }

    override fun showCompletionRegisterProcess() {
        this.showProgressDialog(getString(R.string.progress_dialog_complete_register))
    }

    override fun hideCompletionRegisterProcess() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun showCustomError(error: String) {
        this.toast(error)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showEmptyFieldError() {
        this.toast(getString(R.string.error_endRegister_empty_field))
    }

    override fun showPasswordsDontMatchError() {
        this.toast(getString(R.string.error_endRegister_passwords_dont_match))
    }
}
