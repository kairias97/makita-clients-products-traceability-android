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
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.presenter.IWorkshopFragmentPresenter
import ni.com.fetesa.makitamovil.presenter.implementations.WorkshopFragmentPresenterImpl
import ni.com.fetesa.makitamovil.ui.activities.LoginActivity
import ni.com.fetesa.makitamovil.ui.activities.OrderHeaderActivity
import ni.com.fetesa.makitamovil.ui.adapters.ProductAdapter
import ni.com.fetesa.makitamovil.ui.adapters.WorkshopAdapter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IWorkshopFragmentView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WorkshopFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WorkshopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkshopFragment : Fragment(), IWorkshopFragmentView, WorkshopAdapter.OnOrderSelectedListener {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mTextView: TextView
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mWorkshopAdapter: WorkshopAdapter
    private lateinit var mWorkshopPresenter: IWorkshopFragmentPresenter

    private lateinit var mButtonShowAll: Button

    private var mIsQuoted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_workshop, container, false)
        mTextView = view.findViewById(R.id.textView_label_no_orders)
        mRecyclerView = view.findViewById(R.id.recycler_orders)
        mButtonShowAll = view.findViewById(R.id.button_show_all)

        mWorkshopAdapter = WorkshopAdapter(ArrayList<OrderHeader>(),this)
        mRecyclerView.adapter = mWorkshopAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.setHasFixedSize(true)


        mWorkshopPresenter = WorkshopFragmentPresenterImpl(this, MakitaRemoteDataSource.instance, SharedPrefManager(
                activity.getSharedPreferences(SharedPrefManager.PreferenceFiles.UserSharedPref.toString(),
                        Context.MODE_PRIVATE)
        ))

        mButtonShowAll.setOnClickListener {
            if(mWorkshopPresenter.mIsQuoted){
                mWorkshopPresenter.getAllOrders()
            }
            else{
                mWorkshopPresenter.getQuotedOrders()
            }
        }

        activity.title = "Órdenes en cotización"

        mTextView.visibility = View.GONE
        mIsQuoted = true

        mWorkshopPresenter.getQuotedOrders()
        return view
    }

    override fun onResume() {
        super.onResume()
        mWorkshopPresenter.getQuotedOrders()
    }

    // TODO: Rename method, update argument and hook method into UI event

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        fun onShowLoadingOrdersProgress()
        fun onHideLoadingOrdersProgress()
        fun onShowCustomMessage(msg: String)
        fun onShowError()
    }

    override fun showLoadingOrdersProgress() {
        mListener?.onShowLoadingOrdersProgress()
    }

    override fun hideLoadingOrdersProgress() {
        mListener?.onHideLoadingOrdersProgress()
    }

    override fun showCustomMessage(msg: String) {
        mListener?.onShowCustomMessage(msg)
    }

    override fun showError() {
        mListener?.onShowError()
    }

    override fun navigateToOrderHeader(order: OrderHeader) {
        val intent = Intent(activity, OrderHeaderActivity::class.java)
        intent.putExtra("order",order)
        startActivity(intent)
    }

    override fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun setOrdersList(data: List<OrderHeader>) {
        if(mWorkshopPresenter.mIsQuoted){
            mButtonShowAll.text = getString(R.string.button_show_all_orders)
            activity.title = "Órdenes en cotización"
        }
        else{
            mButtonShowAll.text = getString(R.string.button_show_quoted_orders)
            activity.title = "Historial de órdenes"
        }
        if(data.count()>0){
            (mRecyclerView.adapter as? WorkshopAdapter)!!.setOrdersList(data.reversed().toMutableList())
            (mRecyclerView.adapter as? WorkshopAdapter)!!.notifyDataSetChanged()
            mTextView.visibility = View.GONE
        }
        else{
            mTextView.visibility = View.VISIBLE
        }
    }

    override fun onOrderSelected(orderHeader: OrderHeader) {
        navigateToOrderHeader(orderHeader)
    }


    companion object {
        fun newInstance(listener: OnFragmentInteractionListener): WorkshopFragment {
            val fragment = WorkshopFragment()
            fragment.mListener = listener
            return fragment
        }
    }
}// Required empty public constructor
