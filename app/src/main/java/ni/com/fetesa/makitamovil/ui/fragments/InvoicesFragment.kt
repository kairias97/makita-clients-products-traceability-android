package ni.com.fetesa.makitamovil.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R

/**
 * Created by kevin on 13/5/2018.
 */
class InvoicesFragment: Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAddButton: FloatingActionButton
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
        return view

    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onAddInvoicesSelected()
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