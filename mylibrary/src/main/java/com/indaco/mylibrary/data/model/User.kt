package com.indaco.mylibrary.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class User(@PrimaryKey var email: String): Parcelable {
    companion object {
        const val KEY = "key_user"
    }
}