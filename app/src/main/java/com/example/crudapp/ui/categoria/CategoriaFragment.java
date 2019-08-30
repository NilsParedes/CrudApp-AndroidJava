package com.example.crudapp.ui.categoria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.crudapp.R;
import com.example.crudapp.beans.Categoria;
import com.example.crudapp.helpers.CategoriaHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoriaFragment extends Fragment {

    private CategoriaViewModel categoriaViewModel;

    private ListView listView;

    ArrayList<Categoria> listaCategoria;

    CategoriaHelper categoriaHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriaViewModel =
                ViewModelProviders.of(this).get(CategoriaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categoria, container, false);

        listView = (ListView)root.findViewById(R.id.lv_categoria);

        FloatingActionButton fab = root.findViewById(R.id.categoria_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nombre");

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CategoriaHelper categoriaHelper = new CategoriaHelper(getActivity());

                        Categoria categoria = new Categoria();
                        categoria.setNombre(input.getText().toString());
                        categoriaHelper.insertar(categoria);
                        listar();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        listar();

        return root;
    }

    private void listar() {
        CategoriaHelper categoriaHelper = new CategoriaHelper(getActivity());
        listaCategoria = categoriaHelper.listar();
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getActivity(), android.R.layout.simple_list_item_1,listaCategoria);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View vistafila,
                                    int posicion, long id) {
                Toast.makeText(getActivity(), listaCategoria.get(posicion).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lv_categoria) {
            int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle(R.string.elija_una_opcion);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case R.id.mnuEditar:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nombre");

                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                input.setText(listaCategoria.get(position).getNombre());

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CategoriaHelper categoriaHelper = new CategoriaHelper(getActivity());

                        Categoria categoria = new Categoria();
                        categoria.setId(listaCategoria.get(position).getId());
                        categoria.setNombre(input.getText().toString());
                        categoriaHelper.actualizar(categoria);
                        Toast.makeText(getActivity(), "Categoria actualizada correctamente", Toast.LENGTH_SHORT).show();
                        listar();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                break;
            case R.id.mnuEliminar:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("¿ Seguro de eliminar la ategoria ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Eliminar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CategoriaHelper u = new CategoriaHelper(getContext());
                                boolean res = u.eliminar(listaCategoria.get(position).getId());
                                if(res){
                                    Toast.makeText(getActivity(), "Categoria eliminada correctamente", Toast.LENGTH_SHORT).show();
                                    listar();
                                }else{
                                    Toast.makeText(getActivity(), "La categoría se encuentra en uso", Toast.LENGTH_SHORT).show();
                                }
                                //Snackbar.make(getView(), "Medida eliminada correctamente", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}