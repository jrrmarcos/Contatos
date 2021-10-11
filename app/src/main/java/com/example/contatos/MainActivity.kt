package com.example.contatos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contatos.adapter.ContatosAdapter
import com.example.contatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnContatoClickListener {
    companion object Extras {
        const val EXTRA_CONTATO = "EXTRA_CONTATO"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var contatoActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarContatoActivityResultLauncher: ActivityResultLauncher<Intent>

    //Data Source
    private val contatosList: MutableList<Contato> = mutableListOf()

    //Layout Manager
    private val contatoLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    //Adapter
    private val contatosAdapter: ContatosAdapter by lazy {
        ContatosAdapter(this, contatosList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        //Incializando lista de contatos
        inicializarContatoList()

        //Associar Adapter e LayoutManager ao RecyclerView
        activityMainBinding.contatosRv.adapter = contatosAdapter
        activityMainBinding.contatosRv.layoutManager = contatoLayoutManager

        contatoActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            if(resultado.resultCode==RESULT_OK){
                resultado.data?.getParcelableExtra<Contato>(EXTRA_CONTATO)?.apply{
                    contatosList.add(this)
                    contatosAdapter.notifyDataSetChanged()
                }
            }
        }

        editarContatoActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if(resultado.resultCode== RESULT_OK){
                val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO,-1)
                resultado.data?.getParcelableExtra<Contato>(EXTRA_CONTATO)?.apply{
                    if(posicao!=null && posicao!=-1){
                        contatosList[posicao] = this
                        contatosAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        activityMainBinding.adicionarContatoFab.setOnClickListener{
            contatoActivityResultLauncher.launch(Intent(this, ContatoActivity::class.java))
        }

    }

    private fun inicializarContatoList(){
        for (indice in 1..10){
            contatosList.add(
                Contato(
                    "Nome ${indice}",
                    "Sobrenome ${indice}",
                    indice
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.adicionarContatoMi ->{
            contatoActivityResultLauncher.launch(Intent(this, ContatoActivity::class.java))
            true
        } else -> {
            false;
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = contatosAdapter.posicao

        return when (item.itemId) {
            R.id.editarContatoMi -> {
                //Editar Contato
                val contato = contatosList[posicao]
                val editarContatoIntent = Intent(this, ContatoActivity::class.java)
                editarContatoIntent.putExtra(EXTRA_CONTATO, contato)
                editarContatoIntent.putExtra(EXTRA_POSICAO, posicao)
                editarContatoActivityResultLauncher.launch(editarContatoIntent)

                true
            }
            R.id.removerContatoMi -> {
                //Remover Contato
                contatosList.removeAt(posicao)
                contatosAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onContatoClick(posicao: Int){
        val contato = contatosList[posicao]
        val consultarContatoIntent = Intent(this, ContatoActivity::class.java)
        consultarContatoIntent.putExtra(EXTRA_CONTATO, contato)
        startActivity(consultarContatoIntent)
    }


}