package com.pingpongpacket.truckview.models

import android.os.Parcel
import android.os.Parcelable


class Weighing(var code: String, var date: String,
               var checkPointName: String, var plate: String,
               var driver: String, var license: String,
               var configVehicle: String, var operatorName: String,
               var netWeight: Int, var provider: String,
               var product: String, var client: String): Parcelable {
    constructor(parcel: Parcel): this(parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readInt(), parcel.readString(),
            parcel.readString(), parcel.readString())


    override fun writeToParcel(p0: Parcel?, p1: Int) {
        if (p0 != null){
            p0.writeString(code)
            p0.writeString(date)
            p0.writeString(checkPointName)
            p0.writeString(plate)
            p0.writeString(driver)
            p0.writeString(license)
            p0.writeString(configVehicle)
            p0.writeString(operatorName)
            p0.writeString(provider)
            p0.writeString(product)
            p0.writeString(client)
            p0.writeInt(netWeight)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weighing> {
        override fun createFromParcel(parcel: Parcel): Weighing {
            return Weighing(parcel)
        }

        override fun newArray(size: Int): Array<Weighing?> {
            return arrayOfNulls(size)
        }
    }

}