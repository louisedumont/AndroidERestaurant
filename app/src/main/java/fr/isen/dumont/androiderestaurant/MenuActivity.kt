package fr.isen.dumont.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.dumont.androiderestaurant.network.Category
import fr.isen.dumont.androiderestaurant.network.MenuResult
import fr.isen.dumont.androiderestaurant.network.NetworkConstants
import fr.isen.dumont.androiderestaurant.network.Plats
import org.json.JSONObject

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = (intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? DishsType) ?: DishsType.STARTER // donne une valeur par défaut type STARTER on get/récupère les infos après avoir put, si le cast ne fonctionne pas (null) on met par défaut l'entrée dans DishsType
        setContent {
            MenuView(type)
        }
        Log.d("lifeCycle", "Menu Activity - OnCreate")
    }

    override fun onPause() {
        Log.d("lifeCycle", "Menu Activity - OnPause")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "Menu Activity - OnResume")
    }

    override fun onDestroy() {
        Log.d("lifeCycle", "Menu Activity - onDestroy")
        super.onDestroy()
    }

    companion object { //avoir une cste liée à la classe MenuActivity, lier les 2 (dans l'intent), variable statique
        val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
    }
}
@Composable
fun MenuView(type: DishsType){
    val category = remember {
        mutableStateOf<Category?>(null)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(type.title())
        LazyColumn {//colonnes pour gérer un grand nombre de colonnes
            category.value?.let {
                items(it.items) {
                    dishRow(it)
                }

            }
        }
    }
    postData(type, category)
}


@Composable
fun dishRow(dish : Plats){
    Card{
        Text(text = dish.name)
    }
}



@Composable
fun postData(type: DishsType, category: MutableState<Category?>) {
    val currentCategory=type.title()
    val context = LocalContext.current
    val queue= Volley.newRequestQueue(context)
    val params = JSONObject()
    params.put(NetworkConstants.ID_SHOP, 1)
    val request = JsonObjectRequest(
        Method.POST,
        NetworkConstants.URL,
        params,
        {
           Log.d("request", it.toString(2))
            val result = GsonBuilder().create().fromJson((it.toString()), MenuResult::class.java)
            val filteredResult = result.data.first{ it.name == currentCategory }
            category.value = filteredResult
            Log.d("Result", "")
            //Log.d("parse", "")
        },
        {
            Log.e("request", it.toString())

        }
    )
    queue.add(request)
}


