package fr.isen.dumont.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.dumont.androiderestaurant.network.MenuResult
import fr.isen.dumont.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = (intent.getSerializableExtra(CATEGORY_EXTRA_KEY) as? DishsType) ?: DishsType.STARTER // donne une valeur par défaut type STARTER on get/récupère les infos après avoir put, si le cast ne fonctionne pas (null) on met par défaut l'entrée dans DishsType
        setContent {
            MenuView()
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
fun MenuView() {
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
            val result= GsonBuilder().create().fromJson((it.toString()), MenuResult::class.java)
            Log.d("parse", "")
        },
        {
            Log.e("request", it.toString())

        }
    )
    queue.add(request)
}


