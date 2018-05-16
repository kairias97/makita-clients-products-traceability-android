package ni.com.fetesa.makitamovil.ui.fragmentViews

import ni.com.fetesa.makitamovil.model.Invoice

/**
 * Created by dusti on 14/05/2018.
 */
interface IInvoiceFragmentView {
    fun showInvoiceLoadingProgress()
    fun hideInvoiceLoadingProgress()
    fun showCustomMessage(msg: String)
    fun showError()
    fun setInvoicesList(data: MutableList<Invoice>)
    fun navigateToInvoiceDetail(invoice: Invoice)
}