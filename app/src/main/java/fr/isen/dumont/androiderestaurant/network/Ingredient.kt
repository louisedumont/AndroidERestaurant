package fr.isen.dumont.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredient(@SerializedName("name_fr") val name: String): Serializable //pas de body car ici on veut juste faire apparaître notre data
