package ni.com.fetesa.makitamovil.ui.fragmentViews

import ni.com.fetesa.makitamovil.model.MakitaProfile
import ni.com.fetesa.makitamovil.model.UserFidelizationPoints

/**
 * Created by dusti on 11/05/2018.
 */
interface IProfileFragmentView {
    fun showLoadingProfile()
    fun showLoadingMakitaPoints()
    fun hideLoadingProfile()
    fun hideLoadingMakitaPoints()
    fun showError()
    fun showCustomMessage(message: String)
    fun loadProfile(data: MakitaProfile)
    fun showMakitaPoints(points: UserFidelizationPoints)
}