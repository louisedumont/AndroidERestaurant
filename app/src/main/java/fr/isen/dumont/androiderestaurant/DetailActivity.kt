package fr.isen.dumont.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import fr.isen.dumont.androiderestaurant.network.Plats

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Plats
        setContent(){
            val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ")?:"" //convertir les ingrédients en ce qu'on veut avec map ici on a une liste de strings et non une liste d'ingrédients, le plat est optionnel donc tout est optionnel derrière
            Text(text = ingredient)
        }
    }

    companion object {
        val DISH_EXTRA_KEY = "DISH_EXTRA_KEY"
    }
}