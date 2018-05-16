package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.InvoiceBinding
import ni.com.fetesa.makitamovil.model.InvoiceBindingRequest
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IInvoiceBindingPresenter
import ni.com.fetesa.makitamovil.ui.views.IInvoiceBindingView

/**
 * Created by dusti on 14/05/2018.
 */
class InvoiceBindingPresenterImpl: IInvoiceBindingPresenter {

    private val mInvoiceBindingView: IInvoiceBindingView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(iInvoiceBindingView: IInvoiceBindingView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mInvoiceBindingView = iInvoiceBindingView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun bindInvoice(invoiceID: String) {
        mInvoiceBindingView.showBindingInvoiceProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.bindUserInvoice(token, InvoiceBindingRequest(invoiceID),object: IMakitaResponseCallback<InvoiceBinding>{
            override fun onSuccess(response: InvoiceBinding) {
                mInvoiceBindingView.hideBindingInvoiceProgress()
                getProductsByInvoice(response.invoiceID)
            }

            override fun onCustomMessage(message: String) {
                mInvoiceBindingView.hideBindingInvoiceProgress()
                mInvoiceBindingView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mInvoiceBindingView.hideBindingInvoiceProgress()
                mInvoiceBindingView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mInvoiceBindingView.navigateToLogin()
            }

            override fun onNetworkFailure() {
                mInvoiceBindingView.hideBindingInvoiceProgress()
                mInvoiceBindingView.showError()
            }
        })
    }

    override fun getProductsByInvoice(invoiceID: Long) {
        mInvoiceBindingView.showGettingProductsProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getProductsByInvoice(token,invoiceID,object: IMakitaResponseCallback<List<Product>>{
            override fun onSuccess(response: List<Product>) {
                mInvoiceBindingView.setProductsList(response.toMutableList())
                mInvoiceBindingView.hideGettingProductsProgress()
            }

            override fun onCustomMessage(message: String) {
                mInvoiceBindingView.hideGettingProductsProgress()
                mInvoiceBindingView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mInvoiceBindingView.hideGettingProductsProgress()
                mInvoiceBindingView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mInvoiceBindingView.navigateToLogin()
            }

            override fun onNetworkFailure() {
                mInvoiceBindingView.hideGettingProductsProgress()
                mInvoiceBindingView.showError()
            }
        })
    }

}