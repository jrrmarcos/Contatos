package com.example.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.contatos.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {
    private ActivityContatoBinding activityContatoBinding;
    private int posicao = -1;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());

        setContentView(activityContatoBinding.getRoot());

        activityContatoBinding.salvarBt.setOnClickListener(
                (View view) -> {
                    contato = new Contato(
                            activityContatoBinding.nomeEt.getText().toString(),
                            activityContatoBinding.sobrenomeEt.getText().toString(),
                            Integer.parseInt(activityContatoBinding.telefoneEt.getText().toString())
                            );

                    Intent resultadoIntent = new Intent();
                    resultadoIntent.putExtra(MainActivity.EXTRA_CONTATO, contato);

                    //Se foi adição, devolver posição também
                    if(posicao!=-1){
                        resultadoIntent.putExtra(MainActivity.EXTRA_POSICAO, posicao);
                    }

                    setResult(RESULT_OK,resultadoIntent);
                    finish();
                }
        );

        //Verificando se é uma adição ou consulta e preenchimento de campos
        posicao = getIntent().getIntExtra(MainActivity.EXTRA_POSICAO,-1);
        contato = getIntent().getParcelableExtra(MainActivity.EXTRA_CONTATO);

        if(contato!=null){
            activityContatoBinding.nomeEt.setText(contato.getNome());
            activityContatoBinding.sobrenomeEt.setText(contato.getSobrenome());
            activityContatoBinding.telefoneEt.setText(Integer.toString(contato.getTelefone()));

            if(posicao==-1){
                for(int i=0; i<activityContatoBinding.getRoot().getChildCount(); i++){
                    activityContatoBinding.getRoot().getChildAt(i).setEnabled(false);
                }

                activityContatoBinding.nomeEt.setEnabled(false);
                activityContatoBinding.sobrenomeEt.setEnabled(false);
                activityContatoBinding.telefoneEt.setEnabled(false);
                activityContatoBinding.salvarBt.setVisibility(View.GONE);
            }
        }
    }
}