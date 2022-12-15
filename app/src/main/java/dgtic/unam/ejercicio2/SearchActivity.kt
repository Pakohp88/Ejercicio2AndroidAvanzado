package dgtic.unam.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import dgtic.unam.ejercicio2.databinding.ActivityDeleteBinding
import dgtic.unam.ejercicio2.databinding.ActivitySearchBinding
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.100.74:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun search(view: View) {
        if(!binding.idSearch.text.isEmpty()){
            val urlJSON = "http://" + ipPuerto + "/id/" + binding.idSearch.text.toString()
            var cadena = ""
            val jsonRequest = object : JsonObjectRequest(Method.GET,
                urlJSON, null,
                Response.Listener<JSONObject> { response ->
                    binding.outText3.text = response.get("cuenta").toString() + " " + response.get("nombre").toString() + "\n"
                },
                Response.ErrorListener {
                    binding.outText3.text = getString(R.string.error)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                    return headers
                }
            }
            volleyAPI.add(jsonRequest)
        }
        else{
            Toast.makeText(this, "Introduce un ID valido", Toast.LENGTH_SHORT).show()
            binding.idSearch.error = getString(R.string.invalid)
        }
    }
}