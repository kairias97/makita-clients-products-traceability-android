package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.Product
import ni.com.fetesa.makitamovil.presenter.IInvoiceDetailPresenter
import ni.com.fetesa.makitamovil.ui.views.IInvoiceDetailView

/**
 * Created by dusti on 16/05/2018.
 */
class InvoiceDetailPresenterImpl: IInvoiceDetailPresenter {
    private val mInvoiceDetailView: IInvoiceDetailView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(invoiceDetailView: IInvoiceDetailView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mInvoiceDetailView = invoiceDetailView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getProductsByInvoice(invoiceID: Long) {
        mInvoiceDetailView.showLoadingProductsProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getProductsByInvoice(token, invoiceID, object: IMakitaResponseCallback<List<Product>>{
            override fun onSuccess(response: List<Product>) {
                mInvoiceDetailView.setProductsList(response.toMutableList())
                mInvoiceDetailView.hideLoadingProductsProgress()
            }

            override fun onCustomMessage(message: String) {
                mInvoiceDetailView.hideLoadingProductsProgress()
                mInvoiceDetailView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mInvoiceDetailView.hideLoadingProductsProgress()
                mInvoiceDetailView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
            }

            override fun onNetworkFailure() {
                mInvoiceDetailView.hideLoadingProductsProgress()
                mInvoiceDetailView.showError()
            }
        })
    }

}