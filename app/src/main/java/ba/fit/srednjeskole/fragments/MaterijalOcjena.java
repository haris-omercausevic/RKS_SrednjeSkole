package ba.fit.srednjeskole.fragments;


import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MySession;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.MaterijalOcjenaVM;
import ba.fit.srednjeskole.model.MaterijalVM;
import ba.fit.srednjeskole.model.UIKorisnik;
import okhttp3.ResponseBody;
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
    MaterijalVM materijal;
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
    private RatingBar ratingBar;
    private UIKorisnik korisnik;

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
            korisnik = MySession.readAccessSharedPreferences(MyApp.getContext());
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
        //layoutStars = view.findViewById(R.id.layoutStars);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

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
                    Uri uri = Uri.parse(materijal.getUrl());

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle(materijal.getNaziv());

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, materijal.getNaziv());

                    //request.setMimeType("application/pdf");
                    DownloadManager downloadManager = (DownloadManager) MyApp.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(getActivity(), "Starting download...", Toast.LENGTH_SHORT).show();
                }
            });

            btnOcijeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ratingBar.getRating() == 0){
                        Toast.makeText(getActivity(), "Ocjena ne moze biti 0", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(korisnik == null)
                            return;

                        MaterijalOcjenaVM materijalOcjena = new MaterijalOcjenaVM(){};
                        materijalOcjena.Ocjena = (int)ratingBar.getRating();
                        materijalOcjena.UcenikId = korisnik.KorisnikId;
                        materijalOcjena.MaterijalId = materijal.MaterijalId;

                        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
                        IApiService client = retrofit.create(IApiService.class);

                        Call<MaterijalOcjenaVM> call = client.OcijeniMaterijal(materijalOcjena);
                        call.enqueue(new Callback<MaterijalOcjenaVM>() {
                            @Override
                            public void onResponse(Call<MaterijalOcjenaVM> call, Response<MaterijalOcjenaVM> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(getActivity(), "Ocjena uspjesno pohranjena!", Toast.LENGTH_SHORT).show();
                                    HideOcjeniMaterijal();
                                }
                            }

                            @Override
                            public void onFailure(Call<MaterijalOcjenaVM> call, Throwable t) {
                                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });


            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    switch ((int)ratingBar.getRating()){
                        case 1: txtOcjenaOpis.setText("Loše!");break;
                        case 2: txtOcjenaOpis.setText("Nako!");break;
                        case 3: txtOcjenaOpis.setText("Može proć!");break;
                        case 4: txtOcjenaOpis.setText("Dobar!");break;
                        case 5: txtOcjenaOpis.setText("Odličan!");break;
                    }
                }
            });

            BindIsOcijenjeno();
        }

        return view;
}

    private void BindIsOcijenjeno() {
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);

        if(korisnik == null)
            return;

        Call<String> call = client.GetMaterijalOcjenaIsOcijenjeno(materijal.getMaterijalId(), korisnik.KorisnikId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                String result = String.valueOf(response.body());
                if(result == "true"){
                    HideOcjeniMaterijal();
                }
                else{
                   ShowOcjeniMaterijal();
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
        txtOcjenaOpis.setText("Materijal je vec ocijenjen!");
        ratingBar.setVisibility(View.INVISIBLE);
        btnOcijeni.setVisibility(View.INVISIBLE);
    }
    private void ShowOcjeniMaterijal() {
        txtOcjenaOpis.setText("");
        ratingBar.setVisibility(View.VISIBLE);
        btnOcijeni.setVisibility(View.VISIBLE);
    }
}