package ni.com.fetesa.makitamovil.presenter

import ni.com.fetesa.makitamovil.model.MakitaProfile

/**
 * Created by dusti on 11/05/2018.
 */
interface IProfileFragmentPresenter {
    fun getProfile()
    fun getMakitaPoints()
    fun updateProfile(data: MakitaProfile)
}