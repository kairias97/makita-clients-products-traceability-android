package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.utils.DateUtil

/**
 * Created by dusti on 14/05/2018.
 */
class ProductsViewHolder: RecyclerView.ViewHolder {

    private val mDescripcionTextView: TextView
    private val mSerialTextView: TextView
    private val mPurchasedDateTextView: TextView
    private val mPurchasedStoreTextView: TextView

    constructor(itemView: View): super(itemView){
        mDescripcionTextView = itemView.findViewById(R.id.textView_product_description)
        mSerialTextView = itemView.findViewById(R.id.textView_product_serial)
        mPurchasedDateTextView = itemView.findViewById(R.id.textView_product_purchasedDate)
        mPurchasedStoreTextView = itemView.findViewById(R.id.textView_product_purchasedStore)
    }
    fun bindData(product: Product, listener: ProductAdapter.OnProductSelecteListener){
        mDescripcionTextView.text = product.description
        //mSerialTextView.visibility = View.GONE
        if(product.serialNumber != null && product.serialNumber != ""){
            mSerialTextView.text = product.serialNumber
        }
        else{
            mSerialTextView.text = "Se requiere ingresar serial"
        }
        mPurchasedDateTextView.text = DateUtil.parseDateStringToFormat(product.purchaseDate,"yyyy-mm-dd","dd/mm/yy")
        mPurchasedStoreTextView.text = product.purchaseStore

        itemView.setOnClickListener {
            listener.onProductSelected(product)
        }
    }
}