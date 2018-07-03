package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.utils.DateUtil

/**
 * Created by dusti on 02/07/2018.
 */
class WorkshopViewHolder:RecyclerView.ViewHolder {
    private val mTxtOrderID: TextView
    private val mTxtOrderStatus: TextView
    private val mTxtProductDescription: TextView
    private val mTxtCreatedDate: TextView
    private val mTxtOrderStore: TextView

    constructor(itemView: View):super(itemView){
        mTxtOrderID = itemView.findViewById(R.id.textView_fetesaOrderID)
        mTxtOrderStatus = itemView.findViewById(R.id.textView_orderStatus)
        mTxtProductDescription = itemView.findViewById(R.id.textView_productDescription)
        mTxtCreatedDate = itemView.findViewById(R.id.textView_product_purchasedDate)
        mTxtOrderStore = itemView.findViewById(R.id.textView_product_purchasedStore)
    }

    fun bindData(orderHeader: OrderHeader, listener: WorkshopAdapter.OnOrderSelectedListener){
        mTxtOrderID.text = "Órden #${orderHeader.fetesaOrderID.toString()}"
        mTxtOrderStatus.text = "Estado: ${orderHeader.orderStatus.name}"
        mTxtProductDescription.text = orderHeader.equipmentDescription
        mTxtCreatedDate.text = DateUtil.parseDateStringToFormat(orderHeader.createdDate.substring(0,10),"yyyy-mm-dd","dd/mm/yy")
        mTxtOrderStore.text = orderHeader.distributionCenter

        itemView.setOnClickListener {
            listener.onOrderSelected(orderHeader)
        }
    }
}