package fr.isen.dumont.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import fr.isen.dumont.androiderestaurant.basket.Basket
import fr.isen.dumont.androiderestaurant.basket.BasketActivity
import androidx.compose.runtime.*
import org.json.JSONObject
//import androidx.compose.material3.SnackbarHostController
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import com.google.android.material.snackbar.Snackbar
import fr.isen.dumont.androiderestaurant.basket.BasketItem


fun updateCartItemCount(context: Context, itemCount: Int) {
    val preferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putInt("cartItemCount", itemCount)
    editor.apply()
}


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
        val basket = Basket.current(context)

        // Afficher l'icône de panier avec la pastille
        CartIconWithBadge(basketItems = basket.items)

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
                Text(dish?.name ?: "",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(70.dp),

                    )
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
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
                // modifier = Modifier.span
                //.indent(16.dp)
                //.spanStyle(SpanStyle(fontSize = 20.sp)) // Style des puces
            )

            //Button(
              //  onClick = {
                //    Toast.makeText(context, "Total commande", Toast.LENGTH_SHORT).show()
                  //  (context as? ComponentActivity)?.finish() // Terminer l'activité actuelle pour revenir à l'écran précédent (HomeActivity)
                //},
                //colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                //shape = RoundedCornerShape(0.dp), // Rectangular shape with 0 dp rounded corners
                //modifier = Modifier
                  //  .padding(horizontal = 16.dp, vertical = 8.dp)
                    //.align(Alignment.CenterHorizontally)
            //) {
               // Text(text = "AJOUTER AU PANIER")}


           // ThreeButtonsRow(dish)

            QuantitySelector(dish = dish)
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

       // Row(
         //   modifier = Modifier
           //     .fillMaxWidth()
             //   .padding(16.dp),
            //horizontalArrangement = Arrangement.SpaceEvenly,
            //verticalAlignment = Alignment.CenterVertically
        //) //{
            //Button(
              //  onClick = {
                    // Action pour le bouton "Sur place"
                //},
                //modifier = Modifier
                  //  .weight(1f)
                    //.fillMaxWidth()
                    //.padding(1.dp),
                //colors = ButtonDefaults.buttonColors(
                  //  backgroundColor // Couleur rouge pour le bouton "Sur place"
                //)
            //) {
              //  Text(text = "Sur place")
            //}

            //Button(
              //  onClick = {
                    // Action pour le bouton "Emporter"
                //},
                //modifier = Modifier
                   // .weight(1f)
                    //.fillMaxWidth()
                   // .padding(1.dp),
                //colors = ButtonDefaults.buttonColors(
                  //  backgroundColor // Couleur rouge pour le bouton "Emporter"
                //)
            //) {
                //Text(text = "Emporter")
            //}

            //Button(
              //  onClick = {
                    // Action pour le bouton "Livraison"
                //},
                //modifier = Modifier
                  //  .weight(1f)
                    //.fillMaxWidth()
                    //.padding(1.dp),
                //colors = ButtonDefaults.buttonColors(
                  //  backgroundColor //  Couleur rouge pour le bouton "Livraison"
                //)
           // ) {
                //Text(text = "Livraison")
            //}
        //}
    }

    //val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ")?:"" //convertir les ingrédients en ce qu'on veut avec map ici on a une liste de strings et non une liste d'ingrédients, le plat est optionnel donc tout est optionnel derrière
    //Text(ingredient)
    //}
    // }


    companion object {
        val DISH_EXTRA_KEY = "DISH_EXTRA_KEY"
    }
}


@Composable
fun QuantitySelector(dish: Plats?) {
    var quantity by remember {mutableStateOf(1) }

    //dish price string to float
    val priceInt = dish?.prices?.first()?.price?.toFloat() ?: 0f
    var totalPrice = priceInt * quantity
    val context = LocalContext.current
    val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ") ?: ""

    Text(text = ingredient,
        fontSize = 17.sp,
        textAlign = TextAlign.Center,
        )

    Spacer(modifier = Modifier.size(200.dp))

    Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
    ) {

            Button(onClick = { if (quantity > 1) quantity-- }) {
                Text(
                    text = "-",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(0.2.dp))

            Text(
                text = "$quantity",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(0.2.dp))

            Button(onClick = { quantity++ }) {
                Text(
                    text = "+",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,

                    )
            }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ajoutez vos éléments à l'intérieur de cette colonne
        Text(
            text = "Total: $totalPrice €",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )

        Button(onClick = {
            Toast.makeText(context, "Ajouté à votre panier!", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, BasketActivity::class.java)
            context.startActivity(intent)
        }) {
            Image(
                painter = painterResource(id = R.drawable.caddi), // Remplacez "votre_image" par le nom de votre image
                contentDescription = null, // Ajoutez une description si nécessaire
                modifier = Modifier.size(40.dp) // Modifier la taille de l'image selon vos besoins
            )
        }


       /* Button(onClick = {
            updateCartItemCount(context, quantity)
            // Création de l'objet JSON avec les informations du plat
            val jsonObject = JSONObject().apply {
                put("name", dish?.name)
                put("price", totalPrice)
                put("quantity", quantity)
            }

            // Écriture de l'objet JSON dans un fichier
            val filename = "basket.json"
            val fileContents = jsonObject.toString()
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
            // Affichage d'une Snackbar pour informer l'utilisateur
            //SnackbarHostController.current?.showSnackbar("Plat ajouté au panier")
        }) {
            Text("Commander")
            }
*/

       Button(onClick = {
            if (dish != null) {
                Basket.current(context).add(dish, quantity, context)
            }
        }) {
            Text("Commander")
        }

    }

}


@Composable
fun CartIconWithBadge(basketItems: List<BasketItem>) {
    // Utilisez un MutableState pour stocker le nombre d'articles dans le panier
    var totalItems by remember { mutableStateOf(0) }

    // Mettez à jour la valeur du MutableState chaque fois que le panier change
    totalItems = basketItems.sumBy { it.count }

    // Utilisez un Box pour superposer l'icône du panier et la pastille
    Box(contentAlignment = Alignment.Center) {
        // Utilisez un Row pour aligner l'icône du panier et le texte de la pastille horizontalement
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icône de panier
            Image(
                painter = painterResource(id = R.drawable.caddi),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            // Espacement entre l'icône de panier et la pastille
            Spacer(modifier = Modifier.width(8.dp))

            // Affichez la pastille uniquement si le nombre d'articles est supérieur à zéro
            if (totalItems > 0) {
                // Pastille indiquant le nombre d'articles dans le panier
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .offset(y = (-8).dp) // Décalage vers le haut
                        .padding(horizontal = 4.dp) // Espacement autour du texte de la pastille
                ) {
                    Text(
                        text = totalItems.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}
