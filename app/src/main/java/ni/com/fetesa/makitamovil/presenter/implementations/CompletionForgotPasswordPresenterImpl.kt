package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.PasswordResetCompletion
import ni.com.fetesa.makitamovil.model.PasswordResetCompletionResponse
import ni.com.fetesa.makitamovil.presenter.ICompletionForgotPasswordPresenter
import ni.com.fetesa.makitamovil.presenter.ICompletionRegisterPresenter
import ni.com.fetesa.makitamovil.ui.views.ICompletionForgoPasswordView
import ni.com.fetesa.makitamovil.ui.views.IVerificationCodeForgotPasswordView

/**
 * Created by dusti on 01/07/2018.
 */
class CompletionForgotPasswordPresenterImpl: ICompletionForgotPasswordPresenter {

    private val mCompletionForgotPasswordView : ICompletionForgoPasswordView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(completionForgotPasswordView: ICompletionForgoPasswordView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mCompletionForgotPasswordView = completionForgotPasswordView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun resetPasswordCompletion(user: String, code: String, requestID: String, newPassword: String) {
        mCompletionForgotPasswordView.showSavingPasswordProgress()
        mMakitaRemoteDataSource.resetPasswordCompletion(PasswordResetCompletion(user, code, requestID, newPassword), object: IMakitaResponseCallback<PasswordResetCompletionResponse>{
            override fun onSuccess(response: PasswordResetCompletionResponse) {
                mCompletionForgotPasswordView.hideSavingkPasswordProgress()
                mCompletionForgotPasswordView.showCustomMessage(response.message)
                if(response.isSuccessful){
                    mCompletionForgotPasswordView.navigateToLogin()
                }
            }

            override fun onNetworkFailure() {
                mCompletionForgotPasswordView.hideSavingkPasswordProgress()
                mCompletionForgotPasswordView.showError()
            }

            override fun onCustomMessage(message: String) {
                mCompletionForgotPasswordView.hideSavingkPasswordProgress()
                mCompletionForgotPasswordView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mCompletionForgotPasswordView.hideSavingkPasswordProgress()
                mCompletionForgotPasswordView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mCompletionForgotPasswordView.navigateToLogin()
            }
        })
    }
}