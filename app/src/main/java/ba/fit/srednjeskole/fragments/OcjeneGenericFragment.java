package ba.fit.srednjeskole.fragments;



import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MySession;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.OcjenaVM;
import ba.fit.srednjeskole.model.Storage;
import ba.fit.srednjeskole.model.UIKorisnik;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OcjeneGenericFragment extends Fragment {
    private TextView txtUkupanProsjek;
    private ListView lvOcjene;
    private BaseAdapter adapter;
    private int _razred = 0;
    private UIKorisnik _korisnik;

    public OcjeneGenericFragment() {
        // Required empty public constructor
    }

    public static OcjeneGenericFragment newInstance(int razredId) {
        OcjeneGenericFragment fragment = new OcjeneGenericFragment();
        Bundle args = new Bundle();
        args.putInt(MyApp.RazredGenericKey, razredId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _razred = getArguments().getInt(MyApp.RazredGenericKey);
            _korisnik = MySession.readAccessSharedPreferences(MyApp.getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ocjene_generic, container, false);
        lvOcjene = view.findViewById(R.id.listViewOcjene);
        txtUkupanProsjek = view.findViewById(R.id.txtUkupanProsjek);
        if(_razred != 0){
            if(_korisnik != null)
                popuniPodatke(_korisnik.KorisnikId, _razred);
        }

        return view;
    }

    private double IzracunajProsjek(List<OcjenaVM> ocjene){
        Double sum = 0.0;
        if(!ocjene.isEmpty()){
            for(OcjenaVM ocjena : ocjene){
                sum += ocjena.ProsjecnaOcjena;
            }
            return Math.round(sum.doubleValue()/ocjene.size() * 100.0)/100.0;
        }
        return sum;
    }

    private void popuniPodatke(int korisnik, int razred) {
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<List<OcjenaVM>> call = client.GetOcjeneByUcenikRazred(korisnik, razred);
        call.enqueue(new Callback<List<OcjenaVM>>() {
            @Override
            public void onResponse(Call<List<OcjenaVM>> call, Response<List<OcjenaVM>> response) {
                if(response.isSuccessful()){
                    List<OcjenaVM> podaci = response.body();
                    txtUkupanProsjek.setText("Ukupan prosjek: " + IzracunajProsjek(podaci));

                    adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return podaci.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return podaci.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return podaci.get(position).PredajeId;
                        }

                        @Override
                        public View getView(int position, View view, ViewGroup parent) {
                            if (view == null) {
                                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = inflater.inflate(R.layout.stavka_ocjena, parent, false);
                            }

                            TextView txtPredmet = view.findViewById(R.id.txtFirstLine);
                            TextView txtProsjekPredmet = view.findViewById(R.id.txtSecondLine);
                            TextView txtOcjene = view.findViewById(R.id.txtThirdLine);

                            OcjenaVM x = podaci.get(position);

                            txtPredmet.setText(x.Predmet);
                            txtProsjekPredmet.setText("Prosjek: " + x.ProsjecnaOcjena);
                            txtOcjene.setText("Ocjene: " + x.Ocjene);

                            return view;
                        }
                    };
                    lvOcjene.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<OcjenaVM>> call, Throwable t) {

            }
        });


    }

}
