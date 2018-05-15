package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Invoice
import ni.com.fetesa.makitamovil.presenter.IInvoiceFragmentPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.InvoiceFragmentPresenterImpl
import ni.com.fetesa.makitamovil.ui.adapters.InvoiceAdapter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IInvoiceFragmentView

/**
 * Created by kevin on 13/5/2018.
 */
class InvoicesFragment: Fragment(), InvoiceAdapter.OnInvoiceSelectedListener, IInvoiceFragmentView {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAddButton: FloatingActionButton
    private lateinit var mTextView: TextView
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mInvoiceAdapter: InvoiceAdapter
    private lateinit var mInvoiceFragmentPresenter: IInvoiceFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_invoices, container, false)
        mAddButton = view.findViewById(R.id.fab_add_invoice)
        mAddButton.setOnClickListener {
            mListener?.onAddInvoicesSelected()
        }
        mTextView = view.findViewById(R.id.textView_label_no_invoices)
        mRecyclerView = view.findViewById(R.id.recycler_invoices)
        mInvoiceAdapter = InvoiceAdapter(ArrayList<Invoice>(),this)
        mRecyclerView.adapter = mInvoiceAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)
        mInvoiceFragmentPresenter = InvoiceFragmentPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        activity.title = "Facturas Agregadasg"

        mTextView.visibility = View.GONE

        mInvoiceFragmentPresenter.getInvoices()

        return view

    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onInvoiceSelected(invoice: Invoice) {

    }

    override fun onResume() {
        super.onResume()
        mInvoiceFragmentPresenter.getInvoices()
    }

    interface OnFragmentInteractionListener {
        fun onAddInvoicesSelected()
        fun onLoadingInvoices()
        fun onLoadingInvoicesFinished()
        fun onCustomMessage(msg: String)
        fun onError()
    }

    override fun showInvoiceLoadingProgress() {
        mListener?.onLoadingInvoices()
    }

    override fun hideInvoiceLoadingProgress() {
        mListener?.onLoadingInvoicesFinished()
    }

    override fun showCustomMessage(msg: String) {
        mListener?.onCustomMessage(msg)
    }

    override fun showError() {
        mListener?.onError()
    }

    override fun setInvoicesList(data: MutableList<Invoice>) {
        if(data.count()>0){
            (mRecyclerView.adapter as? InvoiceAdapter)!!.setInvoiceList(data)
            (mRecyclerView.adapter as? InvoiceAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        fun newInstance(listener: OnFragmentInteractionListener): InvoicesFragment {
            val fragment = InvoicesFragment()
            fragment.mListener = listener
            return fragment
        }
    }
}