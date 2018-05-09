package ni.com.fetesa.makitamovil.model

/**
 * Created by dusti on 09/05/2018.
 */
class MakitaUserSession {
    var email = ""
    var authToken = ""

    private constructor()

    companion object {
        val instance: MakitaUserSession by lazy { MakitaUserSession() }
    }
}