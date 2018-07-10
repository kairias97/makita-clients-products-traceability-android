package ni.com.fetesa.makitamovil.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.IRegisterPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.RegisterPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IRegisterView
import ni.com.fetesa.makitamovil.utils.toast

class RegisterActivity : BaseActivity(), IRegisterView {

    private lateinit var txtIdentification: EditText
    private lateinit var radioBtnCedula: RadioButton
    private lateinit var radioBtnNumeroCliente: RadioButton
    private lateinit var btnVerificar: Button
    private lateinit var mBtnCode: Button
    private var typeID: Int = 0

    private lateinit var mRegisterPResenter: IRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Regístrate"

        txtIdentification = findViewById(R.id.edit_text_identification_card)
        radioBtnCedula = findViewById(R.id.radio_btn_cedula)
        radioBtnNumeroCliente = findViewById(R.id.radio_btn_idCliente)
        btnVerificar = findViewById(R.id.button_verify_identification_card)
        mBtnCode = findViewById(R.id.button_have_code)

        mRegisterPResenter = RegisterPresenterImpl(this, MakitaRemoteDataSource.instance)

        btnVerificar.setOnClickListener {
            beginRegister()
        }

        mBtnCode.setOnClickListener {
            sendRegisterToConfirmationCode()
        }
    }

    private fun beginRegister(){
        val idCard = txtIdentification.text.toString()
        if(idCard != ""){
            if(radioBtnCedula.isChecked){
                typeID = 2
            }
            else if(radioBtnNumeroCliente.isChecked){
                typeID = 1
            }
            mRegisterPResenter.register(typeID, idCard)
        }
        else{
            showEmptyFieldsError()
        }
    }
    private fun sendRegisterToConfirmationCode(){
        val idCard = txtIdentification.text.toString()
        if(idCard != ""){
            if(radioBtnCedula.isChecked){
                typeID = 2
            }
            else if(radioBtnNumeroCliente.isChecked){
                typeID = 1
            }
            navigateToValidateRegister()
        }
        else{
            showEmptyFieldsError()
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

    override fun showRegistrationProcess() {
        this.showProgressDialog(getString(R.string.progress_dialog_register))
    }

    override fun hideRegistrationProcess() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun showCustomError(msg: String) {
        this.toast(msg)
    }

    override fun navigateToValidateRegister() {
        val intent = Intent(this, ConfirmationRegisterActivity::class.java)
        intent.putExtra("typeID", typeID)
        intent.putExtra("identificationNumber", txtIdentification.text.toString())
        startActivity(intent)
    }

    override fun showEmptyFieldsError() {
        this.toast(getString(R.string.error_register_empty_field))
    }
}
