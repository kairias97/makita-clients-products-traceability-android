package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.OrderQuoteAnswer
import ni.com.fetesa.makitamovil.model.OrderQuoteAnswerResponse
import ni.com.fetesa.makitamovil.presenter.IOrderAnswerReasonPresenter
import ni.com.fetesa.makitamovil.ui.views.IOrderAnswerReasonView

/**
 * Created by dusti on 03/07/2018.
 */
class OrderAnswerReasonPresenterImpl: IOrderAnswerReasonPresenter {
    private val mOrderAnswerReasonView: IOrderAnswerReasonView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(orderAnswerReasonView: IOrderAnswerReasonView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mOrderAnswerReasonView = orderAnswerReasonView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun rejectOrder(orderID: Int, reason: String) {
        mOrderAnswerReasonView.showRejectingOrderProcess()
        val token = "Token ${MakitaUserSession.instance.authToken}"
        mMakitaRemoteDataSource.answerOrder(token, orderID, OrderQuoteAnswer(false,reason),object: IMakitaResponseCallback<OrderQuoteAnswerResponse>{
            override fun onSuccess(response: OrderQuoteAnswerResponse) {
                mOrderAnswerReasonView.hideRejectingOrderProcess()
                mOrderAnswerReasonView.showCustomMessage(response.Message)
                if(response.isSuccessful){
                    mOrderAnswerReasonView.navigateToMain()
                }
            }

            override fun onNetworkFailure() {
                mOrderAnswerReasonView.hideRejectingOrderProcess()
                mOrderAnswerReasonView.showError()
            }

            override fun onCustomMessage(message: String) {
                mOrderAnswerReasonView.hideRejectingOrderProcess()
                mOrderAnswerReasonView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mOrderAnswerReasonView.hideRejectingOrderProcess()
                mOrderAnswerReasonView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mOrderAnswerReasonView.navigateToLogin()
            }
        })
    }
}