package dgtic.unam.ejercicio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.ejercicio2.databinding.ActivityAddBinding
import org.json.JSONArray
import org.json.JSONObject

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var volleyAPI: VolleyAPI
    private val ipPuerto = "192.168.100.74:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        volleyAPI = VolleyAPI(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun guardar(view: View) {

        if (
            !validaCampos(binding.cuenta) ||
            !validaCampos(binding.nombre) ||
            !validaCampos(binding.edad) ||
            !validaCampos(binding.idMateria1) ||
            !validaCampos(binding.nombreMateria1) ||
            !validaCampos(binding.creditosMateria1) ||
            !validaCampos(binding.idMateria2) ||
            !validaCampos(binding.nombreMateria2) ||
            !validaCampos(binding.creditosMateria2)
        ) {
            Toast.makeText(this, "Introduce un dato valido", Toast.LENGTH_SHORT).show()
            return;
        } else {

            val urlJSON = "http://" + ipPuerto + "/agregarestudiante"
            var cadena = ""
            val jsonRequest = object : JsonArrayRequest(urlJSON,
                Response.Listener<JSONArray> { response ->
                    (0 until response.length()).forEach {
                        val estudiante = response.getJSONObject(it)
                        val materia = estudiante.getJSONArray("materias")
                        cadena += estudiante.get("cuenta").toString() + "<-"
                        (0 until materia.length()).forEach {
                            val datos = materia.getJSONObject(it)
                            cadena += datos.get("nombre")
                                .toString() + "	" + datos.get("creditos").toString() + "--"
                        }
                        cadena += "> \n"
                    }
                    Toast.makeText(this, cadena, Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    println(it.toString())
                }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Accept"] = "application/json"
                    return headers
                }

                override fun getBody(): ByteArray {
                    val estudiante = JSONObject()
                    estudiante.put("cuenta", binding.cuenta.text)
                    estudiante.put("nombre", binding.nombre.text)
                    estudiante.put("edad", binding.edad.text)

                    val materias = JSONArray()
                    val itemMaterias = JSONObject()
                    val itemMaterias2 = JSONObject()

                    itemMaterias.put("id", binding.idMateria1.text)
                    itemMaterias.put("nombre", binding.nombreMateria1.text)
                    itemMaterias.put("creditos", binding.creditosMateria1.text)

                    itemMaterias2.put("id", binding.idMateria2.text)
                    itemMaterias2.put("nombre", binding.nombreMateria2.text)
                    itemMaterias2.put("creditos", binding.creditosMateria2.text)

                    materias.put(itemMaterias)
                    materias.put(itemMaterias2)
                    estudiante.put("materias", materias)

                    return estudiante.toString().toByteArray(charset = Charsets.UTF_8)
                }

                override fun getMethod(): Int {
                    return Method.POST
                }
            }
            volleyAPI.add(jsonRequest)
        }
    }

    fun validaCampos(textView: TextView): Boolean {
        if (textView.text.isEmpty()) {
            textView.error = getString(R.string.invalid)
            return false
        }
        return true
    }
}