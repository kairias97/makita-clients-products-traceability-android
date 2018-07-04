package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderDetail

/**
 * Created by dusti on 03/07/2018.
 */
class OrderDetailAdapter: RecyclerView.Adapter<OrderDetailViewHolder> {
    private var details: MutableList<OrderDetail>
    private var listener: OnDetailSelectedListener

    interface OnDetailSelectedListener{
        fun onDetailSelected(orderDetail: OrderDetail)
    }

    constructor(details: MutableList<OrderDetail>, listener: OnDetailSelectedListener){
        this.details = details
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OrderDetailViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return details.count()
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder?, position: Int) {
        (holder as OrderDetailViewHolder).bindData(details[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_order_detail
    }

    fun setDetailsList(list: MutableList<OrderDetail>){
        this.details = list
    }
}