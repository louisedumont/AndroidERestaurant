package fr.isen.dumont.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(@SerializedName("price") val price : String) : Serializable //on récupère seulement les choses qui nous intéréssen plutôt de récupérer tout

