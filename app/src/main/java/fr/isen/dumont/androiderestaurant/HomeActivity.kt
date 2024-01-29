package fr.isen.dumont.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.dumont.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

enum class DishsType {  //regrouper chaque bouton en 1 seule fonction
    STARTER, MAIN, DESSERT
}

interface  MenuInterface {
    fun dishPressed(dishsType: DishsType) //on crée un toast, il faut créer une interface qui va lister les fcts que l'activité  a besoin pour le passer en paramètres
}


class HomeActivity : ComponentActivity(), MenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(this) //this c'est le MainActivity
                }
            }
        }
    }
    override fun dishPressed(dishsType: DishsType){
        Toast.makeText(this,"Voici mon toast", Toast.LENGTH_LONG).show()
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview(menu:MenuInterface) {
    AndroidERestaurantTheme {
        Greeting(HomeActivity())
    }
}

@Composable
fun Greeting(menu: MenuInterface) {
    Column {
        Image(painterResource(R.drawable.ic_launcher_foreground), null)
        Button(onClick = {menu.dishPressed(DishsType.STARTER) }) {
            //Text("Entrees") de cette manière ce n'est pas très adapté il faut appeler les strings dans le fichier .xml
            Text(stringResource(R.string.menu_starter))
        }
        Button(onClick = {menu.dishPressed(DishsType.MAIN)}) {
            //Text("Plats")
            Text(stringResource(R.string.menu_main))

        }
        Button(onClick = {menu.dishPressed(DishsType.DESSERT)}) {
            //Text("Desserts")
            Text(stringResource(R.string.menu_dessert))

        }

        Surface(color = Color.Blue) {
            Text(
                text = "Hi, my name is",
                modifier = Modifier.padding(24.dp)
            )
        }

    }


}
