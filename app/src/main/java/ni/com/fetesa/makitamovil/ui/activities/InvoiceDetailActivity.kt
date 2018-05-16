package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IInvoiceDetailPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.InvoiceDetailPresenterImpl
import ni.com.fetesa.makitamovil.ui.adapters.ProductAdapter
import ni.com.fetesa.makitamovil.ui.views.IInvoiceDetailView
import ni.com.fetesa.makitamovil.utils.toast

class InvoiceDetailActivity : BaseActivity(), IInvoiceDetailView, ProductAdapter.OnProductSelecteListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProductAdapter: ProductAdapter
    private lateinit var mTextView: TextView
    private lateinit var mInvoiceDetailPresenter: IInvoiceDetailPresenter
    private var mInvoiceID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_detail)

        supportActionBar!!.title = "Detalle de factura"

        if(intent.extras != null){
            mInvoiceID = intent.getIntExtra("invoiceID",0)
        }

        mRecyclerView = findViewById(R.id.recycler_products)
        mTextView = findViewById(R.id.textView_label_no_products)
        mProductAdapter = ProductAdapter(ArrayList<Product>(), this)
        mRecyclerView.adapter = mProductAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        mTextView.visibility = View.GONE

        mInvoiceDetailPresenter = InvoiceDetailPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mInvoiceDetailPresenter.getProductsByInvoice(mInvoiceID.toLong())

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

    override fun showLoadingProductsProgress() {
        this.showProgressDialog(getString(R.string.progress_dialog_products_loading))
    }

    override fun hideLoadingProductsProgress() {
        this.hideProgressDialog()
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun setProductsList(data: MutableList<Product>) {
        if(data.count()>0){
            (mRecyclerView.adapter as? ProductAdapter)!!.setProductsList(data)
            (mRecyclerView.adapter as? ProductAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun navigateToProductDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
    override fun onProductSelected(product: Product) {
        navigateToProductDetail(product)
    }
}
