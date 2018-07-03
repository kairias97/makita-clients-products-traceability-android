package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.OrderHeader

/**
 * Created by dusti on 02/07/2018.
 */
class WorkshopAdapter: RecyclerView.Adapter<WorkshopViewHolder> {
    private var orders: MutableList<OrderHeader>
    private var listener: OnOrderSelectedListener

    interface OnOrderSelectedListener{
        fun onOrderSelected(orderHeader: OrderHeader)
    }

    constructor(orders: MutableList<OrderHeader>, listener: OnOrderSelectedListener){
        this.orders = orders
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkshopViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return WorkshopViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.count()
    }

    override fun onBindViewHolder(holder: WorkshopViewHolder?, position: Int) {
        (holder as WorkshopViewHolder).bindData(orders[position], this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_workshop
    }

    fun setOrdersList(list: MutableList<OrderHeader>){
        this.orders = list
    }
}