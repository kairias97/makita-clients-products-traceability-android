package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.OrderQuoteAnswer
import ni.com.fetesa.makitamovil.model.OrderQuoteAnswerResponse
import ni.com.fetesa.makitamovil.presenter.IOrderDetailPresenter
import ni.com.fetesa.makitamovil.ui.views.IOrderDetailView

/**
 * Created by dusti on 03/07/2018.
 */
class OrderDetailPresenterImpl: IOrderDetailPresenter {
    private val mOrderDetailView: IOrderDetailView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(orderDetailView: IOrderDetailView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mOrderDetailView = orderDetailView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun acceptOrRejectQuote(orderID: Int) {
        mOrderDetailView.showAcceptingProgress()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.answerOrder(token, orderID, OrderQuoteAnswer(true, ""), object: IMakitaResponseCallback<OrderQuoteAnswerResponse>{
            override fun onSuccess(response: OrderQuoteAnswerResponse) {
                mOrderDetailView.hideAcceptingProgress()
                mOrderDetailView.showCustomMessage(response.Message)
                if(response.isSuccessful){
                    mOrderDetailView.navigateToMain()
                }
            }

            override fun onNetworkFailure() {
                mOrderDetailView.hideAcceptingProgress()
                mOrderDetailView.showError()
            }

            override fun onCustomMessage(message: String) {
                mOrderDetailView.hideAcceptingProgress()
                mOrderDetailView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mOrderDetailView.hideAcceptingProgress()
                mOrderDetailView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mOrderDetailView.navigateToLogin()
            }
        })
    }
}