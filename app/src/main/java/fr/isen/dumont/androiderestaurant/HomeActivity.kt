package fr.isen.dumont.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.ui.Alignment as ComposeAlignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import fr.isen.dumont.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.isen.dumont.androiderestaurant.network.Plats

enum class DishsType {  //regrouper chaque bouton en 1 seule fonction
    STARTER, MAIN, DESSERT;

    fun title(): String {
        return when (this) {
            DishsType.STARTER -> "Entrées"
            DishsType.MAIN -> "Plats"
            DishsType.DESSERT -> "Desserts"
        }
    }
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*if (showCategoryScreen) {
                        CategoryScreen()
                    } else {*/
                        Greeting(this) //this c'est le MainActivity
                }
            }
        }
    }
    override fun dishPressed(dishsType: DishsType){
        val message = when(dishsType){
            DishsType.STARTER -> "Vous avez cliqué sur Entrées"
            DishsType.MAIN -> "Vous avez cliqué sur Plats"
            DishsType.DESSERT -> "Vous avez cliqué sur Desserts"
        }
        //Toast.makeText(this,"Voici mon toast", Toast.LENGTH_LONG).show()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show() //les toasts ne s'affichent pas sur l'émulateur mais le prof m'a dit que c'était ok
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.CATEGORY_EXTRA_KEY, dishsType) //on put les données
        startActivity(intent)
    }
    override fun onPause() {
        Log.d("lifeCycle", "Home Activity - OnPause")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "Home Activity - OnResume")
    }

    override fun onDestroy() {
        Log.d("lifeCycle", "Home Activity - onDestroy")
        super.onDestroy()
    }



}
    @Composable
    fun CategoryScreen(){



        //variables optionnelles = AU QCM A SAVOIR!!! représente 90% des crashs dans une appli

        var demonstration: Int?=null //? = la variable peut être nullable
        val cast = demonstration ?: 0
        Log.d("DEMO", cast.toString())

        // add(demonstration!!) avec le bang à ne pas faire car ici on dit tkt elle est pas nulle la var sauf qu'avant on a dit qu'elle pouvait être nulle, A NE PAS FAIRE


        demonstration?.let{ //autre manière qui est + élégante
            add(it)
        }.run{
            add(demonstration) //SI DEMONSTRATION EST NULL = IF ELSE
        }

        add(2)


        if(demonstration !=null){   //autre méthode avec le test
            add(demonstration)
        }
    }


    fun add(value:Int?){    //on passe aussi le paramètre en optionnel

    }





//@PreviewParameter(MenuInterface::class)
//interface MenuInterface {
//  fun dishPressed(dishsType: DishsType)
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        Greeting(HomeActivity())
    }
}



//definir la fonction pour les boutons
@Composable
fun ButtonRow(menu: MenuInterface){
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth())
    {

        Button(onClick = {menu.dishPressed(DishsType.STARTER) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White,
            )
        )
        {
            //Text("Entrees") de cette manière ce n'est pas très adapté il faut appeler les strings dans le fichier .xml
            Text(stringResource(R.string.menu_starter))
        }
        Button(onClick = {menu.dishPressed(DishsType.MAIN) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray,
                contentColor = Color.White))
        {
            //Text("Plats")
            Text(stringResource(R.string.menu_main))

        }
        Button(onClick = {menu.dishPressed(DishsType.DESSERT) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray,
                contentColor = Color.White))
        {
            //Text("Desserts")
            Text(stringResource(R.string.menu_dessert))

        }
    }
}





@Composable
fun Greeting(menu: MenuInterface) {
    //definir le fond couleur boutons et les centrer
    //val buttonBackgroundColor = Color.White
    //val buttonModifier = Modifier
    // .background(color = buttonBackgroundColor)
    //.align(Alignment.CenterVertically)
    // Column ( verticalArrangement = Arrangement.Center,
    //  horizontalAlignment = Alignment.CenterHorizontally,
    // modifier = Modifier
    //     .fillMaxSize()
    //    .background(Color.LightGray))
    // {


    Box(
        modifier = Modifier
            // padding(16.dp)
        .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.menuitalian),
            contentDescription = "Photo menu app",
            modifier = Modifier
                .fillMaxSize()
            //.border(width = 3.dp, color = Color.Gray)
            //.clip(RoundedCornerShape(20.dp))
            //.padding((8.dp))


        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(16.dp)
                .align(Alignment.TopCenter)
        ) {


            //Image(painterResource(R.drawable.ic_launcher_foreground), null)
               Box(
                   modifier = Modifier
                        .fillMaxSize()
                        /*.background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 0f
                            )
                        )*/
               )


            // Box(
            //   modifier = Modifier
            //     .fillMaxSize()
            //      .padding(12.dp),
            //    contentAlignment = Alignment.TopCenter
            //  ) {

                Text(
                    text = "Benvenuti chez ItaloResto!",
                    //modifier = Modifier.padding(24.dp)
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                    .align(Alignment.Center)
                )

        }

        ButtonRow(menu = menu)
    }
}