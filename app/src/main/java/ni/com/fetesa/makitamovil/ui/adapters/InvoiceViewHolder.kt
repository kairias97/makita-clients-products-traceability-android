package ni.com.fetesa.makitamovil.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ni.com.fetesa.makitamovil.R
import ni.com.fetesa.makitamovil.model.Invoice
import ni.com.fetesa.makitamovil.utils.DateUtil

/**
 * Created by dusti on 14/05/2018.
 */
class InvoiceViewHolder: RecyclerView.ViewHolder {
    private val mTxtID: TextView
    private val mTxtAmount: TextView
    private val mTxtDate: TextView
    private val mTxtStore: TextView

    constructor(itemView: View): super(itemView){
        mTxtID = itemView.findViewById(R.id.textView_invoice_ID)
        mTxtAmount = itemView.findViewById(R.id.textView_invoice_amount)
        mTxtDate = itemView.findViewById(R.id.textView_invoice_date)
        mTxtStore = itemView.findViewById(R.id.textView_invoice_store)
    }
    fun bindData(invoice: Invoice, listener: InvoiceAdapter.OnInvoiceSelectedListener){
        mTxtID.text = "Factura #${invoice.physicalNumber}"
        mTxtAmount.text = "C$ ${invoice.amount}"
        mTxtStore.text = invoice.purchaseStore
        mTxtDate.text = DateUtil.parseDateStringToFormat(invoice.issuedDate.substring(0,10),"yyyy-mm-dd","dd/mm/yy")
    }
}