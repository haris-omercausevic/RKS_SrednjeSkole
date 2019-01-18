package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MyFragmentUtils;
import ba.fit.srednjeskole.helper.MySession;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.MaterijalVM;
import ba.fit.srednjeskole.model.ObavijestVM;
import ba.fit.srednjeskole.model.PredmetVM;
import ba.fit.srednjeskole.model.Storage;
import ba.fit.srednjeskole.model.UIKorisnik;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaterijaliFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaterijaliFragment extends Fragment {
    private ListView lvMaterijali;
    private ListView lvPreporuka;
    private Spinner cmbRazredi;
    private Spinner cmbPredmeti;
    private int predmetIndex = -1;
    List<PredmetVM> predmeti;
    public MaterijaliFragment() {
        // Required empty public constructor
    }

    public static MaterijaliFragment newInstance() {
        MaterijaliFragment fragment = new MaterijaliFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Materijali");
        View view = inflater.inflate(R.layout.fragment_materijali, container, false);
        lvMaterijali = view.findViewById(R.id.lvMaterijali);
        lvPreporuka = view.findViewById(R.id.lvPreporuka);
        cmbRazredi = view.findViewById(R.id.cmbRazred);
        cmbRazredi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                predmetIndex = 0;
                cmbPredmeti.setSelection(0);
                cmbPredmeti.clearFocus();
                BindPredmeti();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cmbPredmeti = view.findViewById(R.id.cmbPredmet);
        cmbPredmeti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                predmetIndex = position;
                //predmetIndex = cmbPredmeti.getSelectedItemPosition();
                BindMaterijali("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BindPreporuka("");
        return view;
    }

    private void BindPredmeti() {
        int razred = Integer.parseInt(cmbRazredi.getSelectedItem().toString());
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<List<PredmetVM>> call = client.GetPredmetiByRazred(razred);
        call.enqueue(new Callback<List<PredmetVM>>() {
            @Override
            public void onResponse(Call<List<PredmetVM>> call, Response<List<PredmetVM>> response) {
                if(response.isSuccessful()){
                    predmeti = response.body();
                    ArrayList<PredmetVM> predmetiArray = new ArrayList<>();
                    for (int i = 0; i < predmeti.size(); i++){
                        predmetiArray.add(predmeti.get(i));
                    }
                    ArrayAdapter<PredmetVM> adapter = new ArrayAdapter<PredmetVM>(MyApp.getContext(), android.R.layout.simple_spinner_dropdown_item, predmetiArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbPredmeti.setAdapter(adapter);
                }
                else{
                    Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<PredmetVM>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void BindMaterijali(String query) {
        if(predmetIndex == -1)
            return;

        if(predmeti == null)
            return;

        int predmetId = predmeti.get(predmetIndex).PredmetId;
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);

        Call<List<MaterijalVM>> call = client.GetMaterijaliByPredmet(predmetId);
        call.enqueue(new Callback<List<MaterijalVM>>() {
            @Override
            public void onResponse(Call<List<MaterijalVM>> call, Response<List<MaterijalVM>> response) {
                if(response.isSuccessful()){
                    List<MaterijalVM> podaci = response.body();

                    lvMaterijali.setAdapter(new BaseAdapter() {
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
                            return podaci.get(position).getMaterijalId();
                        }

                        @Override
                        public View getView(int position, View view, ViewGroup viewGroup) {
                            if (view == null) {
                                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = inflater.inflate(R.layout.stavka_materijali, viewGroup, false);
                            }

                            TextView txtFirstLine = view.findViewById(R.id.txtFirstLine);
                            TextView txtSecondLine = view.findViewById(R.id.txtSecondLine);

                            MaterijalVM x = podaci.get(position);

                            txtFirstLine.setText(x.getNaziv());
                            txtSecondLine.setText(x.getDetalji());

                            return view;
                        }
                    });
                            lvMaterijali.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    MyFragmentUtils.openAsReplace(getActivity(), R.id.mjestoZaFragment, (Fragment) MaterijalOcjena.newInstance(podaci.get(position)));
                                }
                            });
                }
                else{
                    Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MaterijalVM>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void BindPreporuka(String query) {
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        UIKorisnik korisnik = MySession.readAccessSharedPreferences(MyApp.getContext());
        if(korisnik  == null)
            return;
        if(korisnik.razrediBrojcano.isEmpty()){
            Toast.makeText(MyApp.getContext(), "Korisnik nije ucenik!", Toast.LENGTH_LONG).show();
            return;
        }


        Call<List<MaterijalVM>> call = client.PreporuciMaterijale(korisnik.KorisnikId, Integer.parseInt(korisnik.razrediBrojcano.get(korisnik.razrediBrojcano.size()-1)));
        call.enqueue(new Callback<List<MaterijalVM>>() {
            @Override
            public void onResponse(Call<List<MaterijalVM>> call, Response<List<MaterijalVM>> response) {
                if(response.isSuccessful()) {
                    List<MaterijalVM> podaci = response.body();

                    lvPreporuka.setAdapter(new BaseAdapter() {
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
                            return podaci.get(position).getMaterijalId();
                        }

                        @Override
                        public View getView(int position, View view, ViewGroup viewGroup) {
                            if (view == null) {
                                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = inflater.inflate(R.layout.stavka_materijali, viewGroup, false);

                            }

                            TextView txtFirstLine = view.findViewById(R.id.txtFirstLine);
                            TextView txtSecondLine = view.findViewById(R.id.txtSecondLine);

                            MaterijalVM x = podaci.get(position);

                            txtFirstLine.setText(x.getNaziv());
                            txtSecondLine.setText(x.getDetalji());

                            return view;
                        }
                    });

                    lvPreporuka.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MyFragmentUtils.openAsReplace(getActivity(), R.id.mjestoZaFragment, (Fragment) MaterijalOcjena.newInstance(podaci.get(position)));
                        }
                    });
                }
                else{
                    Toast.makeText(MyApp.getContext(), "Nema materijala za preporuciti!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MaterijalVM>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
