package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.Invoice
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.presenter.IInvoiceFragmentPresenter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IInvoiceFragmentView

/**
 * Created by dusti on 14/05/2018.
 */
class InvoiceFragmentPresenterImpl: IInvoiceFragmentPresenter {

    private val mInvoiceFragmentView: IInvoiceFragmentView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(invoicesfragmentView: IInvoiceFragmentView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mInvoiceFragmentView = invoicesfragmentView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getInvoices() {
        mInvoiceFragmentView.showInvoiceLoadingProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getInvoicesByUser(token, object: IMakitaResponseCallback<List<Invoice>>{
            override fun onSuccess(response: List<Invoice>) {
                mInvoiceFragmentView.setInvoicesList(response.toMutableList())
                mInvoiceFragmentView.hideInvoiceLoadingProgress()
            }

            override fun onCustomMessage(message: String) {
                mInvoiceFragmentView.hideInvoiceLoadingProgress()
                mInvoiceFragmentView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mInvoiceFragmentView.hideInvoiceLoadingProgress()
                mInvoiceFragmentView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mInvoiceFragmentView.navigateToLogin()
            }

            override fun onNetworkFailure() {
                mInvoiceFragmentView.hideInvoiceLoadingProgress()
                mInvoiceFragmentView.showError()
            }
        })
    }
}