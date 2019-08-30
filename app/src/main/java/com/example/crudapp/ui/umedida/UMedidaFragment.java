package com.example.crudapp.ui.umedida;

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
import com.example.crudapp.beans.UMedida;
import com.example.crudapp.helpers.UMedidaHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UMedidaFragment extends Fragment {

    private UMedidaViewModel uMedidaViewModel;

    private ListView listView;

    ArrayList<UMedida> listaUMedida;

    UMedidaHelper uMedidaHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        uMedidaViewModel =
                ViewModelProviders.of(this).get(UMedidaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_umedida, container, false);

        listView = (ListView)root.findViewById(R.id.lv_umedida);

        FloatingActionButton fab = root.findViewById(R.id.umedida_add);
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

                        UMedidaHelper uMedidaHelper = new UMedidaHelper(getActivity());

                            UMedida uMedida = new UMedida();
                            uMedida.setNombre(input.getText().toString());
                            uMedidaHelper.insertar(uMedida);
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
        UMedidaHelper uMedidaHelper = new UMedidaHelper(getActivity());
        listaUMedida = uMedidaHelper.listar();
        ArrayAdapter<UMedida> adapter = new ArrayAdapter<UMedida>(getActivity(), android.R.layout.simple_list_item_1,listaUMedida);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View vistafila,
                                    int posicion, long id) {
                Toast.makeText(getActivity(), listaUMedida.get(posicion).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lv_umedida) {
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

                input.setText(listaUMedida.get(position).getNombre());

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UMedidaHelper uMedidaHelper = new UMedidaHelper(getActivity());

                        UMedida uMedida = new UMedida();
                        uMedida.setId(listaUMedida.get(position).getId());
                        uMedida.setNombre(input.getText().toString());
                        uMedidaHelper.actualizar(uMedida);
                        Toast.makeText(getActivity(), "Medida actualizada correctamente", Toast.LENGTH_SHORT).show();
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
                builder1.setMessage("¿ Seguro de eliminar la medida ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Eliminar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UMedidaHelper u = new UMedidaHelper(getContext());
                                boolean res = u.eliminar(listaUMedida.get(position).getId());

                                if(res){
                                    Toast.makeText(getActivity(), "Medida eliminada correctamente", Toast.LENGTH_SHORT).show();
                                    listar();
                                }else{
                                    Toast.makeText(getActivity(), "La U. de medida se encuentra en uso", Toast.LENGTH_SHORT).show();
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