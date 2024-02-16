package fr.isen.dumont.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.dumont.androiderestaurant.network.Plats
import androidx.compose.material3.ExperimentalMaterial3Api


class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Plats
        setContent() {
            DetailScreen(dish)
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun DetailScreen(dish: Plats?) {
        val context = LocalContext.current
        val pagerState = rememberPagerState {
            dish?.images?.count() ?: 0
        }

        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()) {
            val ingredientList = dish?.ingredients?.joinToString("\n• ") { it.name } ?: ""
            val formattedIngredients = "• $ingredientList"

            TopAppBar({
                Text(dish?.name ?: "")
            })
            HorizontalPager(state = pagerState) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(dish?.images?.get(it))
                        .build(),
                    null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
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
                    Toast.makeText(context, "Total commande", Toast.LENGTH_SHORT).show()
                    (context as? ComponentActivity)?.finish() // Terminer l'activité actuelle pour revenir à l'écran précédent (HomeActivity)
                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                shape = RoundedCornerShape(0.dp), // Rectangular shape with 0 dp rounded corners
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "TOTAL ")}


            ThreeButtonsRow(dish)
            Button(
                onClick = {
                    Toast.makeText(context, "Retour à l'accueil", Toast.LENGTH_SHORT).show()
                    (context as? ComponentActivity)?.finish() // Terminer l'activité actuelle pour revenir à l'écran précédent (HomeActivity)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Retour")
            }
        }
    }

    @Composable
    fun ThreeButtonsRow(dish: Plats?) {
        val context = LocalContext.current
        val backgroundColor = Color.Red

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    // Action pour le bouton "Sur place"
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor // Couleur rouge pour le bouton "Sur place"
                )
            ) {
                Text(text = "Sur place")
            }

            Button(
                onClick = {
                    // Action pour le bouton "Emporter"
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor // Couleur rouge pour le bouton "Emporter"
                )
            ) {
                Text(text = "Emporter")
            }

            Button(
                onClick = {
                    // Action pour le bouton "Livraison"
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor //  Couleur rouge pour le bouton "Livraison"
                )
            ) {
                Text(text = "Livraison")
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