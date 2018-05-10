package ni.com.fetesa.makitamovil.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.IConfirmationRegisterPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ConfirmationRegisterPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IConfirmationRegisterView
import ni.com.fetesa.makitamovil.utils.toast

class ConfirmationRegisterActivity : BaseActivity(), IConfirmationRegisterView {

    private var typeID: Int = 0
    private lateinit var identificationNumber: String

    private lateinit var txtConfirmationCode: EditText
    private lateinit var btnVerificationCode: Button
    private lateinit var mConfirmationRegisterPresenter: IConfirmationRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_register)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Verificar Registro"

        if(intent.extras != null){
            typeID = intent.getIntExtra("typeID",0)
            identificationNumber = intent.getStringExtra("identificationNumber")
        }

        txtConfirmationCode = findViewById(R.id.edit_text_verification_code)
        btnVerificationCode = findViewById(R.id.button_verify_code)

        mConfirmationRegisterPresenter = ConfirmationRegisterPresenterImpl(this, MakitaRemoteDataSource.instance)

        btnVerificationCode.setOnClickListener {
            val code = txtConfirmationCode.text.toString()
            if(code != ""){
                mConfirmationRegisterPresenter.verifyCode(code, typeID, identificationNumber)
            }
            else{
                showEmptyFieldError()
            }
        }
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
        NavUtils.navigateUpFromSameTask(this)
    }
    override fun onBackPressed() {
        returnTop()
    }

    override fun showValidatingCodeProcess() {
        this.showProgressDialog(getString(R.string.progress_dialog_confirmationRegister))
    }

    override fun hideValidatingCodeProcess() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun showCustomError(error: String) {
        this.toast(error)
    }

    override fun showEmptyFieldError() {
        this.toast(R.string.error_register_empty_field)
    }

    override fun navigateToCompletionRegister(registrationID: String) {
        val intent = Intent(this, CompletionRegisterActivity::class.java)
        intent.putExtra("typeID", typeID)
        intent.putExtra("identificationNumber",identificationNumber)
        intent.putExtra("registrationID",registrationID)
        startActivity(intent)
    }
}
