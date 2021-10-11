package com.example.contatos.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contatos.Contato
import com.example.contatos.OnContatoClickListener
import com.example.contatos.R
import com.example.contatos.databinding.LayoutContatoBinding

class ContatosAdapter(
    private val onContatoClickListener: OnContatoClickListener,
    private val contatosList: MutableList<Contato>
): RecyclerView.Adapter<ContatosAdapter.ContatoLayoutHolder>() {

    //Posição que será recuperada pelo menu de contexto
    var posicao: Int = -1

    //ViewHolder
    inner class ContatoLayoutHolder(layoutContatoBinding: LayoutContatoBinding): RecyclerView.ViewHolder(layoutContatoBinding.root),
            View.OnCreateContextMenuListener {
                val nomeTv: TextView = layoutContatoBinding.nomeTv
                val sobrenomeTv: TextView = layoutContatoBinding.sobrenomeTv
                val telefoneTv: TextView = layoutContatoBinding.telefoneTv
                init {
                    itemView.setOnCreateContextMenuListener(this)
                }

                override fun onCreateContextMenu(
                    menu: ContextMenu?,
                    view: View?,
                    menuInfo: ContextMenu.ContextMenuInfo?
                ) {
                    MenuInflater(view?.context).inflate(R.menu.context_manu_main, menu)
                }
            }

    //Quando uma nova célula precisa ser criada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoLayoutHolder {
        //Criar uma nova célula
        val layoutContatoBinding = LayoutContatoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //Criar um holder associado a nova célula
        val viewHolder: ContatoLayoutHolder = ContatoLayoutHolder(layoutContatoBinding)
        return viewHolder
    }

    //Quando for necessário atualizar os valores de uma célula
    override fun onBindViewHolder(holder: ContatoLayoutHolder, position: Int) {
        //Busco o contato
        val contato = contatosList[position]

        //Atualizar os valores do viewHolder
        with(holder){
            nomeTv.text = contato.nome
            sobrenomeTv.text = contato.sobrenome
            telefoneTv.text = contato.telefone.toString()
            itemView.setOnClickListener {
                onContatoClickListener.onContatoClick(position)
            }
            itemView.setOnLongClickListener{
                posicao = position
                false
            }

        }
    }

    override fun getItemCount(): Int = contatosList.size
}