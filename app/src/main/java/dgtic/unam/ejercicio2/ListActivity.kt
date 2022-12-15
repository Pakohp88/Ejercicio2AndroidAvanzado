package dgtic.unam.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.ejercicio2.databinding.ActivityListBinding
import org.json.JSONArray

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.100.74:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)
        loadEstudiantes()
    }

    private fun loadEstudiantes() {
        val urlJSON = "http://"+ipPuerto+"/estudiantesJSON"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest( urlJSON,
            Response.Listener<JSONArray> { response -> (0 until response.length()).forEach {
                val estudiante = response.getJSONObject(it)
                val materia=estudiante.getJSONArray("materias")
                cadena += estudiante.get("cuenta").toString() + "<-"
                (0 until materia.length()).forEach {
                    val datos=materia.getJSONObject(it)
                    cadena += datos.get("nombre").
                    toString() + "	" + datos.get("creditos").toString() + "--"
                }
                cadena+="> \n"
            }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text = getString(R.string.error)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}