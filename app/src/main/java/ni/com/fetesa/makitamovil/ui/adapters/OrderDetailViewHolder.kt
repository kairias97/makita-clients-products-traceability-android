package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_order_detail.view.*
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderDetail

/**
 * Created by dusti on 03/07/2018.
 */
class OrderDetailViewHolder: RecyclerView.ViewHolder {
    private val mTxtPartDescription: TextView
    private val mTxtUnitPrice: TextView
    private val mTxtQuantity: TextView

    constructor(itemView: View): super(itemView){
        mTxtPartDescription = itemView.findViewById(R.id.textView_partDescription)
        mTxtUnitPrice = itemView.findViewById(R.id.textView_unitPrice)
        mTxtQuantity = itemView.findViewById(R.id.textView_quantity)
    }
    fun bindData(orderDetail: OrderDetail, listener: OrderDetailAdapter.OnDetailSelectedListener){
        mTxtPartDescription.text = orderDetail.partDescription
        mTxtQuantity.text = "Cantidad: ${orderDetail.quantity.toString()}"
        mTxtUnitPrice.text = "Precio Unitario: C$${orderDetail.unitPrice.toString()}"

        itemView.setOnClickListener {
            listener.onDetailSelected(orderDetail)
        }
    }
}