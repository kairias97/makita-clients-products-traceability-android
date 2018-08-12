package ni.com.fetesa.makitamovil.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by dusti on 02/07/2018.
 */
data class OrderHeader(@SerializedName("id") val ID: Int,
                       @SerializedName("fetesaOrderID") val fetesaOrderID: Int,
                       @SerializedName("statusID") val statusID: Int,
                       @SerializedName("distributionCenterCode") val distributionCenterCode: String?,
                       @SerializedName("distributionCenter") val distributionCenter: String?,
                       @SerializedName("clientNumber") val clientNumber: Int,
                       @SerializedName("clientName") val clientName: String?,
                       @SerializedName("equipmentCode") val equipmentCode: String?,
                       @SerializedName("serviceCode") val serviceCode: String?,
                       @SerializedName("service") val service: String?,
                       @SerializedName("equipmentDescription") val equipmentDescription: String?,
                       @SerializedName("warrantyDocument") val warrantyDocument: String?,
                       @SerializedName("warrantyDateTime") val warrantyDateTime: String?,
                       @SerializedName("equipmentSerialNumber") val equipmentSerialNumber: String?,
                       @SerializedName("issuedDate") val issuedDate: String?,
                       @SerializedName("receptionObservation") val receptionObservation: String?,
                       @SerializedName("deliveryObservation") val deliveryObservation: String?,
                       @SerializedName("phoneNumber") val phoneNumber: String?,
                       @SerializedName("receivedAccesories") val receivedAccessories: String?,
                       @SerializedName("deliveryTime") val deliveryTime: Int,
                       @SerializedName("createdDate") val createdDate: String?,
                       @SerializedName("registeredBy") val registeredBy: Int,
                       @SerializedName("description") val description: String?,
                       @SerializedName("repairOrderDetails") val repairOrderDetails: List<OrderDetail>,
                       @SerializedName("orderStatus") val orderStatus: OrderStatus): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.createTypedArrayList(OrderDetail),
            parcel.readParcelable(OrderStatus::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeInt(fetesaOrderID)
        parcel.writeInt(statusID)
        parcel.writeString(distributionCenterCode)
        parcel.writeString(distributionCenter)
        parcel.writeInt(clientNumber)
        parcel.writeString(clientName)
        parcel.writeString(equipmentCode)
        parcel.writeString(serviceCode)
        parcel.writeString(service)
        parcel.writeString(equipmentDescription)
        parcel.writeString(warrantyDocument)
        parcel.writeString(warrantyDateTime)
        parcel.writeString(equipmentSerialNumber)
        parcel.writeString(issuedDate)
        parcel.writeString(receptionObservation)
        parcel.writeString(deliveryObservation)
        parcel.writeString(phoneNumber)
        parcel.writeString(receivedAccessories)
        parcel.writeInt(deliveryTime)
        parcel.writeString(createdDate)
        parcel.writeInt(registeredBy)
        parcel.writeString(description)
        parcel.writeTypedList(repairOrderDetails)
        parcel.writeParcelable(orderStatus, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderHeader> {
        override fun createFromParcel(parcel: Parcel): OrderHeader {
            return OrderHeader(parcel)
        }

        override fun newArray(size: Int): Array<OrderHeader?> {
            return arrayOfNulls(size)
        }
    }

}