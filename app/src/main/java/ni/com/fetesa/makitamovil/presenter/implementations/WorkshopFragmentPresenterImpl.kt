package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.OrderHeader
import ni.com.fetesa.makitamovil.presenter.IWorkshopFragmentPresenter
import ni.com.fetesa.makitamovil.ui.fragmentViews.IWorkshopFragmentView

/**
 * Created by dusti on 02/07/2018.
 */
class WorkshopFragmentPresenterImpl: IWorkshopFragmentPresenter {

    private val mWorkshopFragmentView: IWorkshopFragmentView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    override var mIsQuoted:Boolean = true

    constructor(workshopfragmentView: IWorkshopFragmentView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mWorkshopFragmentView = workshopfragmentView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun getQuotedOrders() {
        mWorkshopFragmentView.showLoadingOrdersProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getQuotedOrders(token, object: IMakitaResponseCallback<List<OrderHeader>>{
            override fun onSuccess(response: List<OrderHeader>) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mIsQuoted = true
                mWorkshopFragmentView.setOrdersList(response)
            }

            override fun onNetworkFailure() {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showError()
            }

            override fun onCustomMessage(message: String) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mWorkshopFragmentView.navigateToLogin()
            }
        })
    }

    override fun getAllOrders() {
        mWorkshopFragmentView.showLoadingOrdersProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.getAllOrders(token, object: IMakitaResponseCallback<List<OrderHeader>>{
            override fun onSuccess(response: List<OrderHeader>) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mIsQuoted = false
                mWorkshopFragmentView.setOrdersList(response)
            }

            override fun onNetworkFailure() {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showError()
            }

            override fun onCustomMessage(message: String) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mWorkshopFragmentView.hideLoadingOrdersProgress()
                mWorkshopFragmentView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mWorkshopFragmentView.navigateToLogin()
            }
        })
    }
}