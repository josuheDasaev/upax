package com.dasaevcompany.upax.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Movie : Serializable {

    @SerializedName("title")  var name: String = ""
    @SerializedName("poster_path")  var picture: String = ""
    @SerializedName("vote_average")  var rate: Float = 0f
    @SerializedName("overview")  var description: String = ""
    @SerializedName("release_date")  var date: String = ""

    @PrimaryKey(autoGenerate = true)
    var databaseId = 0
}