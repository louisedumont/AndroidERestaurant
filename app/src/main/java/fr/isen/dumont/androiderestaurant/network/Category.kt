package fr.isen.dumont.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("name_fr") val name : String,
    @SerializedName("items") val item : List<Plats>
):Serializable
