package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.OrderDetail
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.presenter.implementations.OrderDetailPresenterImpl
import ni.com.fetesa.makitamovil.ui.adapters.OrderDetailAdapter
import ni.com.fetesa.makitamovil.ui.views.IOrderDetailView
import ni.com.fetesa.makitamovil.utils.QUOTED_ORDER_ID
import ni.com.fetesa.makitamovil.utils.toast

class OrderDetailActivity : BaseActivity(), OrderDetailAdapter.OnDetailSelectedListener, IOrderDetailView {

    private lateinit var mDetails: List<OrderDetail>
    private lateinit var mOrder: OrderHeader
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mButtonAccept: Button
    private lateinit var mButtonReject: Button
    private lateinit var mTotalTextView : TextView
    private lateinit var mOrderDetailAdapter: OrderDetailAdapter

    private lateinit var mOrderDetailPresenter: OrderDetailPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        if(intent.extras != null){
            mOrder = intent.getParcelableExtra("order")
            mDetails = intent.getParcelableArrayListExtra("details")
        }
        var total: Double = 0.00
        for (detail in mDetails) {
            total += detail.unitPrice * detail.quantity
        }
        mTotalTextView = findViewById<TextView>(R.id.text_total_quotation)
        mTotalTextView.text = "Costo Total: C$ ${total}"
        mRecyclerView = findViewById(R.id.recycler_details)
        mButtonAccept = findViewById(R.id.button_accept)
        mButtonReject = findViewById(R.id.button_reject)

        mOrderDetailAdapter = OrderDetailAdapter(ArrayList<OrderDetail>(), this)
        mRecyclerView.adapter = mOrderDetailAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        (mRecyclerView.adapter as? OrderDetailAdapter)!!.setDetailsList(mDetails.toMutableList())
        (mRecyclerView.adapter as? OrderDetailAdapter)!!.notifyDataSetChanged()

        mOrderDetailPresenter = OrderDetailPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        if(mOrder.orderStatus.NAFCode == QUOTED_ORDER_ID){
            mButtonReject.visibility = View.VISIBLE
            mButtonAccept.visibility = View.VISIBLE
        }
        else{
            mButtonAccept.visibility = View.GONE
            mButtonReject.visibility = View.GONE
        }

        mButtonAccept.setOnClickListener {
            this.showConfirmDialog(titleResId = R.string.message_accepting_quote,
                    messageResId = R.string.message_accepting_quote_label,
                    iconResId = R.drawable.baseline_warning_black_48,
                    positiveBtnResId = R.string.button_accept_quote,
                    negativeBtnResId = R.string.logout_cancel,
                    positiveListener = android.content.DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        mOrderDetailPresenter.acceptOrRejectQuote(mOrder.ID)
                    },
                    negativeListener = android.content.DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
        }

        mButtonReject.setOnClickListener {
            navigateToReasonRejection()
        }

        supportActionBar!!.title = "Detalles de cotización"

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

    override fun onDetailSelected(orderDetail: OrderDetail) {
        //a implementar a futuro en caso de ser necesario
    }

    override fun navigateToReasonRejection() {
        val intent = Intent(this, OrderAnswerReasonActivity::class.java)
        intent.putExtra("order", mOrder)
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showAcceptingProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_order_details_accepting))
    }

    override fun hideAcceptingProgress() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
