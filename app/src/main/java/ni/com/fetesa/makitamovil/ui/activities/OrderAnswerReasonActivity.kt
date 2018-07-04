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
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.presenter.IOrderAnswerReasonPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.OrderAnswerReasonPresenterImpl
import ni.com.fetesa.makitamovil.ui.views.IOrderAnswerReasonView
import ni.com.fetesa.makitamovil.utils.toast

class OrderAnswerReasonActivity : BaseActivity(), IOrderAnswerReasonView {

    private lateinit var mOrder:OrderHeader

    private lateinit var mTxtReason: EditText
    private lateinit var mBtnReject: Button

    private lateinit var mOrderReasonPresenter: IOrderAnswerReasonPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_answer_reason)

        if(intent.extras != null){
            mOrder = intent.getParcelableExtra("order")
        }

        mTxtReason = findViewById(R.id.edit_text_reason)
        mBtnReject = findViewById(R.id.button_reject)

        mOrderReasonPresenter = OrderAnswerReasonPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mBtnReject.setOnClickListener {
            if(mTxtReason.text.toString() != ""){
                mOrderReasonPresenter.rejectOrder(mOrder.ID, mTxtReason.text.toString())
            }
            else{
                showEmptyFields()
            }
        }

        supportActionBar!!.title = "Razón de rechazo de cotización"

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

    override fun showRejectingOrderProcess() {
        this.showProgressDialog(getString(R.string.progress_dialog_rejecting_quote))
    }

    override fun hideRejectingOrderProcess() {
        this.hideProgressDialog()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showEmptyFields() {
        this.toast(R.string.generic_empty_fields)
    }
}
