package ni.com.fetesa.makitamovil.presenter

/**
 * Created by dusti on 14/05/2018.
 */
interface IInvoiceBindingPresenter {
    fun bindInvoice(invoiceID: String)
    fun getProductsByInvoice(invoiceID: Long)
}