package ni.com.fetesa.makitamovil.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ni.com.fetesa.makitamovil.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProductsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment() {

    // TODO: Rename and change types of parameters

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAddButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_products, container, false)
        mAddButton = view.findViewById(R.id.fab_add_products)
        mAddButton.setOnClickListener {
            mListener?.onAddProductsSelected()
        }
        return view

    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onAddProductsSelected()
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
