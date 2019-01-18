package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ba.fit.srednjeskole.GlavniActivity;
import ba.fit.srednjeskole.LoginActivity;
import ba.fit.srednjeskole.MainActivity;
import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MyFragmentUtils;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.UIKorisnik;
import ba.fit.srednjeskole.model.ObavijestVM;
import ba.fit.srednjeskole.helper.MyApiRequest;
import ba.fit.srednjeskole.helper.MyRunnable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ba.fit.srednjeskole.helper.MyConfig.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObavijestiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObavijestiFragment extends Fragment {
    private ListView listView;
    private SearchView searchView;


    public ObavijestiFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ObavijestiFragment newInstance() {
        ObavijestiFragment fragment = new ObavijestiFragment();
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


        getActivity().setTitle("Obavijesti");
        View view = inflater.inflate(R.layout.fragment_obavijesti, container, false);

        listView = view.findViewById(R.id.listViewObavijesti);
        searchView = view.findViewById(R.id.txtPretraga);

searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        do_btnTraziClick(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        do_btnTraziClick(query);
        return false;
    }
});
        searchView.setIconifiedByDefault(false);
        popuniPodatkeTask("");
        return view;
    }

    private void do_btnTraziClick(String query) {
        popuniPodatkeTask(query);
    }

    private void popuniPodatkeTask(final String query){

//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(getString(R.string.ApiBaseUrl))
//                .addConverterFactory(GsonConverterFactory.create());
        //Retrofit retrofit = builder.build();
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<List<ObavijestVM>> call = client.GetObavijestiByNaziv(query);

        call.enqueue(new Callback<List<ObavijestVM>>() {
            @Override
            public void onResponse(Call<List<ObavijestVM>> call, Response<List<ObavijestVM>> response) {
                if(response.isSuccessful()){
                List<ObavijestVM> podaci = response.body();

                listView.setAdapter(new BaseAdapter() {
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
                        return podaci.get(position).ObavijestId;
                    }

                    @Override
                    public View getView(int position, View view, ViewGroup viewGroup) {
                        if (view == null) {
                            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            view = inflater.inflate(R.layout.stavka_obavijesti, viewGroup, false);

                        }

                        TextView txtNaslov = view.findViewById(R.id.txtFirstLine);
                        TextView txtObjavio = view.findViewById(R.id.txtSecondLine);

                        ObavijestVM x = podaci.get(position);

                        txtNaslov.setText(x.getNaslov());
                        txtObjavio.setText(x.getImePrezimeDatumObjave());

                        return view;
                    }
                });


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyFragmentUtils.openAsReplace(getActivity(), R.id.mjestoZaFragment, (Fragment) ObavijestiDetaljiFragment.newInstance(podaci.get(position).ObavijestId));
                    }
                });
                }
                else{
                    Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ObavijestVM>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
