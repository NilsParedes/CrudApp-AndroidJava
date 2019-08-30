package com.example.crudapp.ui.producto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.crudapp.ProductoActivity;
import com.example.crudapp.R;
import com.example.crudapp.beans.Producto;
import com.example.crudapp.helpers.ProductoHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductoFragment extends Fragment {

    private ProductoViewModel productoViewModel;
    private ListView listView;

    ProductoHelper productoHelper;

    ArrayList<Producto> listaProducto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productoViewModel =
                ViewModelProviders.of(this).get(ProductoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_producto, container, false);

        productoHelper=new ProductoHelper(getActivity());

        listaProducto=new ArrayList<Producto>();

        FloatingActionButton fab = root.findViewById(R.id.producto_add);
        listView = (ListView)root.findViewById(R.id.lv_producto);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                //      .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), ProductoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        getVistas();

        return root;
    }

    private void getVistas() {
        ProductoHelper prod = new ProductoHelper(getActivity());
        listaProducto = prod.listar();
        ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(getActivity(), android.R.layout.simple_list_item_1,listaProducto);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View vistafila,
                                    int posicion, long id) {
                Toast.makeText(getActivity(), listaProducto.get(posicion).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lv_producto) {
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
                Intent intent = new Intent(getActivity(), ProductoActivity.class);
                intent.putExtra("id", listaProducto.get(position).getId());
                getActivity().startActivity(intent);
                break;
            case R.id.mnuEliminar:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("¿ Seguro de eliminar el producto ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Eliminar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ProductoHelper p = new ProductoHelper(getContext());
                                productoHelper.eliminar(listaProducto.get(position).getId()); //Falla
                                //Snackbar.make(getView(), "Producto eliminado correctamente", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                Toast.makeText(getActivity(), "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                                getVistas();
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