package ni.com.fetesa.makitamovil.presenter.implementations

import ni.com.fetesa.makitamovil.data.local.SharedPrefManager
import ni.com.fetesa.makitamovil.presenter.IMainPresenter
import ni.com.fetesa.makitamovil.ui.views.IMainView

/**
 * Created by dusti on 01/07/2018.
 */
class MainPresenterImpl: IMainPresenter {

    private val mSharedPrefManager: SharedPrefManager
    private val mMainView: IMainView

    constructor(mainView: IMainView ,sharedPrefManager: SharedPrefManager){
        this.mMainView = mainView
        this.mSharedPrefManager = sharedPrefManager
    }

    override fun executeLogout() {
        mMainView.showLoggingOutProgress()
        mSharedPrefManager.clearPreferences()
        mMainView.hideLoggingOutProgress()
        mMainView.navigateToLogin()
    }
}