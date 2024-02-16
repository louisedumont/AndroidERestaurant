package fr.isen.dumont.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.dumont.androiderestaurant.network.Plats

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Plats
        setContent() {
            DetailScreen(dish)
        }
    }

    @Composable
    fun DetailScreen(dish: Plats?){
            val context = LocalContext.current


            Column(modifier = Modifier.padding(16.dp) .fillMaxWidth() .fillMaxHeight()) {
                val ingredientList = dish?.ingredients?.joinToString("\n• ") { it.name } ?: ""
                val formattedIngredients = "• $ingredientList"

                Text(
                    text = formattedIngredients,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.weight(1f)
                    // modifier = Modifier.span
                    //.indent(16.dp)
                    //.spanStyle(SpanStyle(fontSize = 20.sp)) // Style des puces
                )

                Button(
                    onClick = {
                        Toast.makeText(context, "Retour à l'accueil", Toast.LENGTH_SHORT).show()
                        (context as? ComponentActivity)?.finish() // Terminer l'activité actuelle pour revenir à l'écran précédent (HomeActivity)
                    },
                    modifier = Modifier.padding(16.dp) .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Retour")
                }

            }
        }



    //val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ")?:"" //convertir les ingrédients en ce qu'on veut avec map ici on a une liste de strings et non une liste d'ingrédients, le plat est optionnel donc tout est optionnel derrière
    //Text(ingredient)
    //}
    // }


    companion object {
        val DISH_EXTRA_KEY = "DISH_EXTRA_KEY"
    }
}