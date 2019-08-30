package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crudapp.beans.Categoria;
import com.example.crudapp.beans.Marca;
import com.example.crudapp.beans.Producto;
import com.example.crudapp.beans.UMedida;
import com.example.crudapp.helpers.CategoriaHelper;
import com.example.crudapp.helpers.MarcaHelper;
import com.example.crudapp.helpers.ProductoHelper;
import com.example.crudapp.helpers.UMedidaHelper;

import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    private EditText campoNombre, campoPrecio;
    private ProductoHelper productoHelper;
    private Spinner spinnerCategoria, spinnerMarca, spinnerUMedida;

    private boolean editar = false;
    private int id;

    private boolean valNombre =false, valPrecio = false;

    private CategoriaHelper categoriaHelper;
    private MarcaHelper marcaHelper;
    private UMedidaHelper uMedidaHelper;

    private List<Categoria> listaCategoria;
    private List<Marca> listaMarca;
    private List<UMedida> listaUMedida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        productoHelper = new ProductoHelper(this);

        campoNombre= (EditText) findViewById(R.id.txt_nombre);
        campoPrecio= (EditText) findViewById(R.id.txt_precio);

        spinnerCategoria= (Spinner) findViewById(R.id.spinner_categoria);
        spinnerMarca= (Spinner) findViewById(R.id.spinner_marca);
        spinnerUMedida= (Spinner) findViewById(R.id.spinner_umedida);


        categoriaHelper = new CategoriaHelper(this);
        listaCategoria = categoriaHelper.listar();
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(this,android.R.layout.simple_spinner_dropdown_item, listaCategoria);
        spinnerCategoria.setPrompt("Selecciona una Categoría");
        spinnerCategoria.setAdapter(adapterCategoria);

        marcaHelper = new MarcaHelper(this);
        listaMarca = marcaHelper.listar();
        ArrayAdapter<Marca> adapterMarca = new ArrayAdapter<Marca>(this,android.R.layout.simple_spinner_dropdown_item, listaMarca);
        spinnerMarca.setPrompt("Selecciona una Marca");
        spinnerMarca.setAdapter(adapterMarca);

        uMedidaHelper = new UMedidaHelper(this);
        listaUMedida = uMedidaHelper.listar();
        ArrayAdapter<UMedida> adapterUMedida = new ArrayAdapter<UMedida>(this,android.R.layout.simple_spinner_dropdown_item, listaUMedida);
        spinnerUMedida.setPrompt("Selecciona una Unidad de medida");
        spinnerUMedida.setAdapter(adapterUMedida);

        id =  getIntent().getIntExtra("id", -1);

        if(id != -1){
            editar = true;
            llenarCampos(productoHelper.consultar(id));
        }


    }

    public void llenarCampos(Producto producto){

        campoNombre.setText(producto.getNombre());

        for(int i=0;i<listaCategoria.size();i++) {
            if(listaCategoria.get(i).getId() == producto.getCategoria()) {
                spinnerCategoria.setSelection(i);
            }
        }

        for(int i=0;i<listaMarca.size();i++) {
            if(listaMarca.get(i).getId() == producto.getMarca()) {
                spinnerMarca.setSelection(i);
            }
        }

        for(int i=0;i<listaUMedida.size();i++) {
            if(listaUMedida.get(i).getId() == producto.getUmedida()) {
                spinnerUMedida.setSelection(i);
            }
        }

        campoPrecio.setText(String.valueOf(producto.getPrecio()));

        campoNombre.requestFocus();

    }

    public void onClick(View view) {

        if(validar()){
            Producto producto = new Producto();

            producto.setNombre(campoNombre.getText().toString());
            producto.setCategoria(listaCategoria.get(spinnerCategoria.getSelectedItemPosition()).getId());
            producto.setMarca(listaMarca.get(spinnerMarca.getSelectedItemPosition()).getId());
            producto.setUmedida(listaUMedida.get(spinnerUMedida.getSelectedItemPosition()).getId());
            producto.setPrecio(Float.parseFloat(campoPrecio.getText().toString()));

            if(editar){
                producto.setId(id);
                productoHelper.actualizar(producto);
                Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                productoHelper.insertar(producto);
                //limpiarCampos();
                Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
                //Snackbar.make(view, "Producto registrado correctamente", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }

            Intent i = new Intent(this, NavActivity.class);
            startActivity(i);

        }else {
            Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean validar(){
        if(campoNombre.getText().toString().length() < 1){
            campoNombre.setError("Nombre inválido");
            valNombre = false;
        }else{
            campoNombre.setError(null);
            valNombre = true;
        }

        if(campoPrecio.getText().toString().length() < 1){
            campoPrecio.setError("Precio inválido");
            valPrecio = false;
        }else{
            campoPrecio.setError(null);
            valPrecio = true;
        }

        if(valPrecio && valNombre){
            return true;
        }else {
            return false;
        }
    }

    public void limpiarCampos(){

        campoNombre.setText("");
        campoPrecio.setText("");

        campoNombre.requestFocus();

    }
}
