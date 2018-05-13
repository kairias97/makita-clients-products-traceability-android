package ni.com.fetesa.makitamovil.data.remote.Callbacks

/**
 * Created by kevin on 13/5/2018.
 */
interface IMakitaResponseCallback<T> {
    fun onSuccess(response: T)
    fun onCustomMessage(message: String)
    fun onSessionExpired(message: String)
    fun onNetworkFailure()
}