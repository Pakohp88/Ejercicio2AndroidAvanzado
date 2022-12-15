package dgtic.unam.ejercicio2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicioToolsBar()
    }

    private fun inicioToolsBar(){
        val toolbar: Toolbar =findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer=findViewById(R.id.drawer_layout)
        val toggle= ActionBarDrawerToggle(this, drawer,toolbar,R.string.abrir,R.string.cerrar)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.pakman32)
        iniciarNavegacionView()

    }
    private fun iniciarNavegacionView(){
        val navegacionView: NavigationView =findViewById(R.id.nav_view)
        navegacionView.setNavigationItemSelectedListener(this)
        val headerView: View = LayoutInflater.from(this).inflate(R.layout.header_main, navegacionView,false)
        navegacionView.addHeaderView(headerView)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addOption->{
                startActivity(Intent(this,AddActivity::class.java))
            }
            R.id.listOption ->{
                startActivity(Intent(this,ListActivity::class.java))
            }
            R.id.searchOption ->{
                startActivity(Intent(this,SearchActivity::class.java))
            }
            R.id.deleteOption ->{
                startActivity(Intent(this,DeleteActivity::class.java))
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
