package ni.com.fetesa.makitamovil.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.RecommendedProduct
import ni.com.fetesa.makitamovil.presenter.IRecommendedProductPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.RecommendedProductPresenterImpl
import ni.com.fetesa.makitamovil.ui.adapters.RecommendedProductAdapter
import ni.com.fetesa.makitamovil.ui.views.IRecommendedProductView
import ni.com.fetesa.makitamovil.utils.toast

class RecommendedProductsActivity : BaseActivity(), IRecommendedProductView, RecommendedProductAdapter.OnRecommendationSelectedListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTextView: TextView

    private lateinit var mRecommendedProductAdapter: RecommendedProductAdapter
    private lateinit var mRecommendedProductPresenter: IRecommendedProductPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_products)

        mRecyclerView = findViewById(R.id.recycler_recommended_products)
        mTextView = findViewById(R.id.textView)

        mRecommendedProductAdapter = RecommendedProductAdapter(ArrayList<RecommendedProduct>(),this)
        mRecyclerView.adapter = mRecommendedProductAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        mRecommendedProductPresenter = RecommendedProductPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                this.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mTextView.visibility = View.GONE

        supportActionBar!!.title = "Productos Recomendados"
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecommendedProductPresenter.getRecommendedProducts()
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

    override fun onBackPressed() {
        returnTop()
    }

    override fun showLoadingRecommendedProducts() {
        this.showProgressDialog(getString(R.string.progress_dialog_recommended_products))
    }

    override fun hideLoadingRecommendedProducts() {
        this.hideProgressDialog()
    }

    override fun showError() {
        this.toast(R.string.generic_500_error)
    }

    override fun showCustomMessage(msg: String) {
        this.toast(msg)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun setRecommendationsList(data: MutableList<RecommendedProduct>) {
        if(data.count()>0){
            (mRecyclerView.adapter as? RecommendedProductAdapter)!!.setRecommendationsList(data)
            (mRecyclerView.adapter as? RecommendedProductAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun onRecommendationSelected(recommendation: RecommendedProduct) {
        //en caso que se requiera implementar a futuro
    }
}
