package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.Invoice

/**
 * Created by dusti on 14/05/2018.
 */
class InvoiceAdapter: RecyclerView.Adapter<InvoiceViewHolder> {
    private var invoices: MutableList<Invoice>
    private var listener: OnInvoiceSelectedListener

    interface OnInvoiceSelectedListener{
        fun onInvoiceSelected(invoice: Invoice)
    }

    constructor(invoices: MutableList<Invoice>, listener: OnInvoiceSelectedListener){
        this.invoices = invoices
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return invoices.count()
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder?, position: Int) {
        (holder as InvoiceViewHolder).bindData(invoices[position],this.listener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_invoice
    }
    fun setInvoiceList(list: MutableList<Invoice>){
        this.invoices = list
    }
}