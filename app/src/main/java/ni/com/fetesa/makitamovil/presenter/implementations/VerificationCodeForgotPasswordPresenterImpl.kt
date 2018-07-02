package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.data.remote.Callbacks.IMakitaResponseCallback
import ni.com.fetesa.makitamovil.data.remote.MakitaRemoteDataSource
import ni.com.fetesa.makitamovil.model.MakitaUserSession
import ni.com.fetesa.makitamovil.model.PasswordResetVerification
import ni.com.fetesa.makitamovil.model.PasswordResetVerificationResponse
import ni.com.fetesa.makitamovil.presenter.IVerificationCodeForgotPasswordPresenter
import ni.com.fetesa.makitamovil.ui.views.IVerificationCodeForgotPasswordView

/**
 * Created by dusti on 01/07/2018.
 */
class VerificationCodeForgotPasswordPresenterImpl: IVerificationCodeForgotPasswordPresenter {

    private val mVerificationCodeForgotPasswordView: IVerificationCodeForgotPasswordView
    private val mMakitaRemoteDataSource: MakitaRemoteDataSource
    private val mSharedPrefManager: SharedPrefManager

    constructor(verificationCodeForgotPasswordView: IVerificationCodeForgotPasswordView, makitaRemoteDataSource: MakitaRemoteDataSource, sharedPrefManager: SharedPrefManager){
        this.mVerificationCodeForgotPasswordView = verificationCodeForgotPasswordView
        this.mMakitaRemoteDataSource = makitaRemoteDataSource
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun validateCode(user: String, code: String) {
        mVerificationCodeForgotPasswordView.showVerificationProgress()
        mMakitaRemoteDataSource.resetPasswordVerifyCode(PasswordResetVerification(user,code), object: IMakitaResponseCallback<PasswordResetVerificationResponse>{
            override fun onSuccess(response: PasswordResetVerificationResponse) {
                mVerificationCodeForgotPasswordView.hideVerificationProgress()
                mVerificationCodeForgotPasswordView.showCustomMessage(response.message)
                if(response.isValid){
                    mVerificationCodeForgotPasswordView.navigateToResetPasswordCompletion(response.requestID)
                }
            }

            override fun onNetworkFailure() {
                mVerificationCodeForgotPasswordView.hideVerificationProgress()
                mVerificationCodeForgotPasswordView.showError()
            }

            override fun onCustomMessage(message: String) {
                mVerificationCodeForgotPasswordView.hideVerificationProgress()
                mVerificationCodeForgotPasswordView.showCustomMessage(message)
            }

            override fun onSessionExpired(message: String) {
                mVerificationCodeForgotPasswordView.hideVerificationProgress()
                mVerificationCodeForgotPasswordView.showCustomMessage(message)
                mSharedPrefManager.clearPreferences()
                mVerificationCodeForgotPasswordView.navigateToLogin()
            }
        })
    }
}