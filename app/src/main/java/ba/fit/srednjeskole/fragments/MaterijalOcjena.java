package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.MaterijalVM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaterijalOcjena#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaterijalOcjena extends Fragment {
    private MaterijalVM materijal;
    TextView txtPredmetIRazred;
    TextView txtNazivMaterijala;
    TextView txtNastavnik;
    TextView txtDatum;
    TextView txtRating;
    TextView txtBrojOcjena;
    ImageView imgDownloadMaterijal;

    TextView txtOcjenaOpis;
    Button btnOcijeni;
    private LinearLayout layoutStars;

    public MaterijalOcjena() {
        // Required empty public constructor
    }

    public static MaterijalOcjena newInstance(MaterijalVM materijal) {
        MaterijalOcjena fragment = new MaterijalOcjena();
        Bundle args = new Bundle();
        args.putSerializable(MyApp.MaterijalKey, materijal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            materijal = (MaterijalVM) getArguments().getSerializable(MyApp.MaterijalKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Ocijeni materijal");
        View view = inflater.inflate(R.layout.fragment_materijal_ocjena, container, false);
        txtPredmetIRazred = view.findViewById(R.id.txtPredmetIRazredMaterijala);
        txtNazivMaterijala = view.findViewById(R.id.txtNazivMaterijala);
        txtNastavnik = view.findViewById(R.id.txtNastavnik);
        txtDatum = view.findViewById(R.id.txtDatum);
        txtRating = view.findViewById(R.id.txtRating);
        txtBrojOcjena = view.findViewById(R.id.txtBrojOcjena);
        imgDownloadMaterijal = view.findViewById(R.id.imgDownloadMaterijal);

        txtOcjenaOpis = view.findViewById(R.id.txtOcjenaOpis);
        btnOcijeni = view.findViewById(R.id.btnOcijeni);
        layoutStars = view.findViewById(R.id.layoutStars);
        if (materijal != null) {
            txtPredmetIRazred.setText(materijal.getPredmet() + " - " + Integer.toString(materijal.getRazred()) + " razred");
            txtNazivMaterijala.setText(materijal.getNaziv());
            txtNastavnik.setText("Objavio: " + materijal.getNastavnik());
            txtDatum.setText("Datum objave: " + materijal.getDatum());
            txtRating.setText("Rating: " + String.valueOf(materijal.getRating()));
            txtBrojOcjena.setText("Broj ocjena: " + Integer.toString(materijal.getBrojOcjena()));

            imgDownloadMaterijal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Downloading not yet implemented...", Toast.LENGTH_SHORT).show();
                }
            });

            BindIsOcijenjeno();
        }

        return view;
}

    private void BindIsOcijenjeno() {
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<String> call = client.GetMaterijalOcjenaIsOcijenjeno(materijal.getMaterijalId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                String result = String.valueOf(response.body());
                if(result == "true"){
                    HideOcjeniMaterijal();
                }
                else{
                    btnOcijeni.setVisibility(View.VISIBLE);
                    txtOcjenaOpis.setText("");
                    layoutStars.setVisibility(View.VISIBLE);
                }
                }
                else{
                    Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void HideOcjeniMaterijal() {
        btnOcijeni.setVisibility(View.INVISIBLE);
        txtOcjenaOpis.setText("Materijal je vec ocijenjen!");
        layoutStars.setVisibility(View.INVISIBLE);
    }
}