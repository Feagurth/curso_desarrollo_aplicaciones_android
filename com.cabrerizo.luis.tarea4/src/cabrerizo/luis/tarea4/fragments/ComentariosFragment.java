package cabrerizo.luis.tarea4.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cabrerizo.luis.tarea4.activities.DetalleActivity;
import cabrerizo.luis.tarea4.activities.FotografiaActivity;
import cabrerizo.luis.tarea4.data.Comment;
import cabrerizo.luis.tarea4.data.Store;
import cabrerizo.luis.tarea4.global.Utiles;

import com.cabrerizo.luis.tarea4.R;

public class ComentariosFragment extends Fragment {

	private static final String COMENTARIO = "Comentario";
	private static final String FECHA = "Fecha";
	private View vista;
	private List<HashMap<String, String>> comentarios = new ArrayList<HashMap<String, String>>();
	private SimpleAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String, String> comentario = new HashMap<String, String>();
				String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm",
						Locale.getDefault()).format(Calendar.getInstance()
						.getTime());

				EditText textoComentario = (EditText) vista
						.findViewById(R.id.texto_comentario);

				String valor = textoComentario.getText().toString().trim();

				if (!valor.isEmpty()) {
					comentario.put(COMENTARIO, valor);
					comentario.put(FECHA, fecha);

					comentarios.add(0, comentario);

					adapter.notifyDataSetChanged();

					textoComentario.setText(null);
				}
			}
		};

		ListView lista = (ListView) vista.findViewById(R.id.lista_comentarios);

		Button boton = (Button) vista.findViewById(R.id.boton_comentarios);
		boton.setOnClickListener(listener);

		adapter = new SimpleAdapter(vista.getContext(), comentarios,
				android.R.layout.simple_list_item_2, new String[] { COMENTARIO,
						FECHA }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		lista.setAdapter(adapter);

		ArrayList<Comment> commentarios = null;
		
		int id = getActivity().getIntent().getExtras().getInt("id");
		
		Store tienda = Utiles.locateStore(getActivity().getApplicationContext(), id);

		if (getActivity().getClass() == DetalleActivity.class) {
			commentarios = tienda.getListadoComentarios();
		} else if (getActivity().getClass() == FotografiaActivity.class) {
			commentarios = tienda.getFoto().getListaComentarios();
		}

		for (Comment comment : commentarios) {

			HashMap<String, String> comentario = new HashMap<String, String>();

			comentario.put(COMENTARIO, comment.getComentario());
			comentario.put(FECHA, comment.getFecha());
			comentarios.add(comentario);
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		vista = inflater.inflate(R.layout.fragment_comentarios, container,
				false);

		return vista;
	}

}