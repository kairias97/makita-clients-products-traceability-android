package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.presenter.IPasswordUpdatePresenter
import ni.com.fetesa.makitamovil.presenter.implementations.PasswordUpdatePresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IPasswordUpdateView
import ni.com.fetesa.makitamovil.utils.toast
import android.view.View.OnFocusChangeListener



class PasswordUpdateActivity : BaseActivity(), IPasswordUpdateView {

    private lateinit var mTxtOldPassword: EditText
    private lateinit var mTxtNewPassword: EditText
    private lateinit var mTxtConfirmNewPassword: EditText
    private lateinit var mBtnUpdatePassword: Button

    private lateinit var mLayoutOldPassword: TextInputLayout
    private lateinit var mLayoutNewPassword: TextInputLayout
    private lateinit var mLayoutConfirmNewPassword: TextInputLayout

    private lateinit var mPasswordUpdatePresenter: IPasswordUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_update)

        mTxtOldPassword = findViewById(R.id.edit_text_old_password)
        mTxtNewPassword = findViewById(R.id.edit_text_new_password)
        mTxtConfirmNewPassword = findViewById(R.id.edit_text_confirm_new_password)
        mBtnUpdatePassword = findViewById(R.id.button_update)

        mLayoutOldPassword = findViewById(R.id.text_input_layout_old_password)
        mLayoutNewPassword = findViewById(R.id.text_input_layout_new_password)
        mLayoutConfirmNewPassword = findViewById(R.id.text_input_layout_confirm_new_password)

        mPasswordUpdatePresenter = PasswordUpdatePresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mTxtOldPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtOldPassword)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        mTxtNewPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtNewPassword)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtConfirmNewPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validateText(mTxtConfirmNewPassword)
                validateEqualPasswords(mTxtNewPassword, mTxtConfirmNewPassword)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        mTxtOldPassword.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                validateText(mTxtOldPassword)
            }
        }
        mTxtNewPassword.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                validateText(mTxtNewPassword)
            }
        }
        mTxtConfirmNewPassword.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                validateText(mTxtConfirmNewPassword)
            }
        }

        mBtnUpdatePassword.setOnClickListener {
            if(validateText(mTxtOldPassword) && validateText(mTxtNewPassword) && validateText(mTxtConfirmNewPassword)){
                if(validateEqualPasswords(mTxtNewPassword, mTxtConfirmNewPassword)){
                    val oldPassword: String = mTxtOldPassword.text.toString()
                    val newPassword: String = mTxtNewPassword.text.toString()
                    mPasswordUpdatePresenter.updatePassword(oldPassword, newPassword)
                }
            }
        }

        supportActionBar!!.title = "Actualización de contraseña"
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun validateText(s: EditText): Boolean{
        when(s.id){
            R.id.edit_text_old_password -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutOldPassword.error = null
                    return true
                }
                else{
                    mLayoutOldPassword.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_new_password -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutNewPassword.error = null
                    return true
                }
                else{
                    mLayoutNewPassword.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            R.id.edit_text_confirm_new_password -> {
                if(!TextUtils.isEmpty(s.text)){
                    mLayoutConfirmNewPassword.error = null
                    return true
                }
                else{
                    mLayoutConfirmNewPassword.error = getString(R.string.validate_no_empty_field)
                    return false
                }
            }
            else -> {
                return false
            }
        }
    }

    private fun validateEqualPasswords(s1: EditText, s2: EditText): Boolean{
        if(s1.text.toString().equals(s2.text.toString())){
            mLayoutConfirmNewPassword.error = null
            return true
        }
        else{
            mLayoutConfirmNewPassword.error = getString(R.string.validate_no_equal_passwords)
            return false
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
        //NavUtils.navigateUpFromSameTask(this)
        finish()
    }

    override fun showUpdatingPasswordProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_updating_password))
    }

    override fun hideUpdatingPasswordProgress() {
        this.hideProgressDialog()
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToMain() {
        this.returnTop()
    }
}
