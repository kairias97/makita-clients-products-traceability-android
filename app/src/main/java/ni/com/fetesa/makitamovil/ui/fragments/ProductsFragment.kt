package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IProductsFragmentPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.ProductsFragmentPresenterImpl
import ni.com.fetesa.makitamovil.ui.activities.LoginActivity
import ni.com.fetesa.makitamovil.ui.activities.ProductDetailActivity
import ni.com.fetesa.makitamovil.ui.activities.RecommendedProductsActivity
import ni.com.fetesa.makitamovil.ui.adapters.ProductAdapter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IProductsFragmentView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProductsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment(), IProductsFragmentView, ProductAdapter.OnProductSelecteListener {

    // TODO: Rename and change types of parameters

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAddButton: FloatingActionButton
    private lateinit var mTextView: TextView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mButtonShowRecommendedProducts: Button

    private lateinit var mProductAdapter: ProductAdapter
    private lateinit var mProductPresenter: IProductsFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_products, container, false)
        mAddButton = view.findViewById(R.id.fab_add_products)
        mTextView = view.findViewById(R.id.textView_label_no_products)
        mButtonShowRecommendedProducts = view.findViewById(R.id.button_show_recommended)

        mRecyclerView = view.findViewById(R.id.recycler_products)
        mProductAdapter = ProductAdapter(ArrayList<Product>(),this)
        mRecyclerView.adapter = mProductAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)

        mAddButton.setOnClickListener {
            mListener?.onAddProductsSelected()
        }

        mProductPresenter = ProductsFragmentPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mButtonShowRecommendedProducts.setOnClickListener {
            this.navigateToRecommendedProducts()
        }

        activity.title = "Productos"

        mTextView.visibility = View.GONE

        mProductPresenter.getProducts()
        return view

    }

    override fun onResume() {
        super.onResume()
        mProductPresenter.getProducts()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onAddProductsSelected()
        fun onLoadingProducts()
        fun onLoadingProductsFinished()
        fun onError()
        fun onCustomMessage(msg: String)
    }

    override fun showLoadingProducts() {
        mListener?.onLoadingProducts()
    }

    override fun hideLoadingProducts() {
        mListener?.onLoadingProductsFinished()
    }

    override fun showCustomMessage(msg: String) {
        mListener?.onCustomMessage(msg)
    }

    override fun showError() {
        mListener?.onError()
    }
    override fun onProductSelected(product: Product) {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

    override fun setProductList(data: MutableList<Product>) {
        if(data.count()>0){
            (mRecyclerView.adapter as? ProductAdapter)!!.setProductsList(data)
            (mRecyclerView.adapter as? ProductAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRecommendedProducts() {
        val intent = Intent(activity, RecommendedProductsActivity::class.java)
        startActivity(intent)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(listener: OnFragmentInteractionListener): ProductsFragment {
            val fragment = ProductsFragment()
            fragment.mListener = listener
            return fragment
        }
    }
}// Required empty public constructor
