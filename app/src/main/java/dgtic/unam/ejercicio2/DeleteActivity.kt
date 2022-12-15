package dgtic.unam.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.ejercicio2.databinding.ActivityDeleteBinding
import dgtic.unam.ejercicio2.databinding.ActivityListBinding
import org.json.JSONArray

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.100.74:8080"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)
    }





    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun delete(view: View) {
        if(!binding.idDelete.text.isEmpty()){
            var id: String = binding.idDelete.text.toString()
            val urlJSON = "http://" + ipPuerto + "/borrarestudiante/" + id
            var cadena = ""
            val jsonRequest = object : JsonArrayRequest(urlJSON,
                Response.Listener<JSONArray> { response ->
                    (0 until response.length()).forEach {
                        val estudiante = response.getJSONObject(it)
                        val materia = estudiante.getJSONArray("materias")
                        cadena += estudiante.get("cuenta").toString() + "<-"
                        (0 until materia.length()).forEach {
                            val datos = materia.getJSONObject(it)
                            cadena += datos.get("nombre").toString() + "	" +
                                    datos.get("creditos").toString() + "--"
                        }
                        cadena += "> \n"
                    }
                    binding.outText2.text = cadena
                },
                Response.ErrorListener {
                    binding.outText2.text = getString(R.string.error)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                    return headers
                }

                override fun getMethod(): Int {
                    return return Method.DELETE
                }
            }
            volleyAPI.add(jsonRequest)
        }
        else{
            Toast.makeText(this, "Introduce un ID valido", Toast.LENGTH_SHORT).show()
            binding.idDelete.error = getString(R.string.invalid)
        }
    }
}