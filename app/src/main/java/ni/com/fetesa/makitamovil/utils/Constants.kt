package ni.com.fetesa.makitamovil.utils

/**
 * Created by dusti on 08/05/2018.
 */

const val MAKITA_MAIN_URL = "https://centralderiesgo.net/makita/api/v1/"
//const val MAKITA_MAIN_URL = "http://186.1.15.181/Makita/api/v1/"
const val URL_LOGIN = "authentication/login/"
const val URL_REGISTRATION = "registrations/"
const val URL_REGISTRATION_VERIFICATION = "registrations/verification"
const val URL_REGISTRATION_COMPLETION = "registrations/completion"
const val URL_PROFILE = "clients/profile"
const val URL_POINTS = "clients/points"
const val URL_PRODUCTS = "clients/products"
const val URL_INVOICES = "clients/invoices"
const val URL_INVOICES_PRODUCTS = "clients/invoices/{invoiceID}/products"
const val URL_INVOICES_BINDING = "clients/invoices/binding"
const val URL_PRODUCTS_Warranty = "clients/products/{productID}/warranty"
const val URL_UPDATE_PASSWORD = "authentication/password/update/"
const val URL_RESET_PASSWORD = "authentication/passwordReset/request/"
const val URL_RESET_PASSWORD_VERIFY_CODE = "authentication/passwordReset/codeVerification"
const val URL_RESET_PASSWORD_COMPLETION = "authentication/passwordReset/submission/"
const val URL_ORDERS_QUOTED_GET = "clients/repairOrders/quoted/"
const val URL_ORDERS_GET = "clients/repairOrders/"
const val URL_ORDERS_ANSWER = "clients/repairOrders/{orderID}/answer/"
const val URL_RECOMMENDED_PRODUCTS_GET = "products/recommendations/"


const val TYPE_ID_NUMERO_CLIENTE = 1
const val TYPE_ID_CEDULA = 2
const val QUOTED_ORDER_ID = "044"