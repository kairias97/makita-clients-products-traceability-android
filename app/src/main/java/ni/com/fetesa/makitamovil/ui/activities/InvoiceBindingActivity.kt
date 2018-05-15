package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IInvoiceBindingPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.InvoiceBindingPresenterImpl
import ni.com.fetesa.makitamovil.ui.adapters.ProductAdapter
import ni.com.fetesa.makitamovil.ui.views.IInvoiceBindingView
import ni.com.fetesa.makitamovil.utils.toast

class InvoiceBindingActivity : BaseActivity(), IInvoiceBindingView, ProductAdapter.OnProductSelecteListener {

    private lateinit var mTxtInvoice: EditText
    private lateinit var mBtnRegisterInvoice: Button
    private lateinit var mTextViewNoProducts: TextView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProductAdapter: ProductAdapter
    private lateinit var mInvoiceBindingPresenter: IInvoiceBindingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_binding)

        mTxtInvoice = findViewById(R.id.edit_text_invoice_number)
        mBtnRegisterInvoice = findViewById(R.id.button_bind_invoice)
        mTextViewNoProducts = findViewById(R.id.textView_label_no_products)
        mRecyclerView = findViewById(R.id.recycler_products)
        mProductAdapter = ProductAdapter(ArrayList<Product>(),this)
        mRecyclerView.adapter = mProductAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        mInvoiceBindingPresenter = InvoiceBindingPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mBtnRegisterInvoice.setOnClickListener {
            if(mTxtInvoice.text.toString() != ""){
                mInvoiceBindingPresenter.bindInvoice(mTxtInvoice.text.toString())
            }
            else{
                showCustomMessage(getString(R.string.error_register_empty_field))
            }
        }
        mTextViewNoProducts.visibility = View.VISIBLE

        supportActionBar!!.title = "Registro de factura"
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
        NavUtils.navigateUpFromSameTask(this)
    }
    override fun onBackPressed() {
        returnTop()
    }

    override fun showBindingInvoiceProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_invoice_binding))
    }

    override fun hideBindingInvoiceProgress() {
        this.hideProgressDialog()
    }

    override fun showGettingProductsProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_products_loading))
    }

    override fun hideGettingProductsProgress() {
        this.hideProgressDialog()
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun onProductSelected(product: Product) {
        this.toast("Se ha seleccionado un producto")
    }

    override fun setProductsList(data: MutableList<Product>) {
        if(data.count()>0){
            (mRecyclerView.adapter as? ProductAdapter)!!.setProductsList(data)
            (mRecyclerView.adapter as? ProductAdapter)!!.notifyDataSetChanged()
            mTextViewNoProducts.visibility = View.GONE
        }
        else{
            mTextViewNoProducts.visibility = View.VISIBLE
        }
    }
}
