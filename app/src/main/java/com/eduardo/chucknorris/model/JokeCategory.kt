package com.eduardo.chucknorris.model

import android.os.Parcel
import android.os.Parcelable

data class JokeCategory(val name: String ): Parcelable {

    constructor(parcel: Parcel): this(
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
       return 0
    }

    companion object CREATOR : Parcelable.Creator<JokeCategory> {
        override fun createFromParcel(source: Parcel): JokeCategory {
            return JokeCategory(source)
        }

        override fun newArray(size: Int): Array<JokeCategory?> {
            return arrayOfNulls(size)
        }
    }
}