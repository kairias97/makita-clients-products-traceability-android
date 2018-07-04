package ni.com.fetesa.makitamovil.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 02/07/2018.
 */
data class OrderHeader(@SerializedName("id") val ID: Int, @SerializedName("fetesaOrderID") val fetesaOrderID: Int,
                       @SerializedName("statusID") val statusID: Int, @SerializedName("distributionCenterCode") val distributionCenterCode: String,
                       @SerializedName("distributionCenter") val distributionCenter: String, @SerializedName("clientNumber") val clientNumber: Int,
                       @SerializedName("clientName") val clientName: String, @SerializedName("equipmentCode") val equipmentCode: String,
                       @SerializedName("serviceCode") val serviceCode: String, @SerializedName("service") val service: String,
                       @SerializedName("equipmentDescription") val equipmentDescription: String, @SerializedName("warrantyDocument") val warrantyDocument: String,
                       @SerializedName("warrantyDateTime") val warrantyDateTime: String, @SerializedName("equipmentSerialNumber") val equipmentSerialNumber: String,
                       @SerializedName("issuedDate") val issuedDate: String, @SerializedName("receptionObservation") val receptionObservation: String,
                       @SerializedName("deliveryObservation") val deliveryObservation: String, @SerializedName("phoneNumber") val phoneNumber: String,
                       @SerializedName("receivedAccesories") val receivedAccessories: String, @SerializedName("deliveryTime") val deliveryTime: Int,
                       @SerializedName("createdDate") val createdDate: String, @SerializedName("registeredBy") val registeredBy: Int,
                       @SerializedName("description") val description: String, @SerializedName("repairOrderDetails") val repairOrderDetails: List<OrderDetail>,
                       @SerializedName("orderStatus") val orderStatus: OrderStatus): Parcelable {
    constructor(parcel: Parcel):this(parcel.readInt(),parcel.readInt(),parcel.readInt(),parcel.readString(),parcel.readString(),parcel.readInt(),parcel.readString(),parcel.readString(),
            parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),parcel.readString(),
            parcel.readString(),parcel.readString(),parcel.readInt(),parcel.readString(),parcel.readInt(),parcel.readString(), arrayListOf<OrderDetail>().apply {
        parcel.readArrayList(OrderDetail::class.java.classLoader)
    }, parcel.readParcelable(OrderStatus::class.java.classLoader))

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(ID)
        dest?.writeInt(fetesaOrderID)
        dest?.writeInt(statusID)
        dest?.writeString(distributionCenterCode)
        dest?.writeString(distributionCenter)
        dest?.writeInt(clientNumber)
        dest?.writeString(clientName)
        dest?.writeString(equipmentCode)
        dest?.writeString(serviceCode)
        dest?.writeString(service)
        dest?.writeString(equipmentDescription)
        dest?.writeString(warrantyDocument)
        dest?.writeString(warrantyDateTime)
        dest?.writeString(equipmentSerialNumber)
        dest?.writeString(issuedDate)
        dest?.writeString(receptionObservation)
        dest?.writeString(deliveryObservation)
        dest?.writeString(phoneNumber)
        dest?.writeString(receivedAccessories)
        dest?.writeInt(deliveryTime)
        dest?.writeString(createdDate)
        dest?.writeInt(registeredBy)
        dest?.writeString(description)
        dest?.writeList(repairOrderDetails)
        dest?.writeParcelable(orderStatus,0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<OrderHeader>{
        override fun createFromParcel(parcel: Parcel): OrderHeader {
            return OrderHeader(parcel)
        }

        override fun newArray(size: Int): Array<OrderHeader?> {
            return arrayOfNulls(size)
        }
    }
}