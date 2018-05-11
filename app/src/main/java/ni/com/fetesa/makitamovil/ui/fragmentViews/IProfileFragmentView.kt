package ni.com.fetesa.makitamovil.ui.fragmentViews

import ni.com.fetesa.makitamovil.model.MakitaProfile

/**
 * Created by dusti on 11/05/2018.
 */
interface IProfileFragmentView {
    fun showLoadingProfile()
    fun hideLoadingProfile()
    fun showError()
    fun showCustomError(error: String)
    fun loadProfile(data: MakitaProfile)
}