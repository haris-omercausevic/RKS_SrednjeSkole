package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.ObavijestVM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObavijestiDetaljiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObavijestiDetaljiFragment extends Fragment {
    private int _obavijestId = 0;
    ObavijestVM _obavijest;

    TextView txtNaslovObavijesti;
    TextView txtObjavio;
    TextView txtDatumObjave;
    TextView txtTekstObavijesti;

    public ObavijestiDetaljiFragment() {
        // Required empty public constructor
    }

    public static ObavijestiDetaljiFragment newInstance(int obavijestId) {
        ObavijestiDetaljiFragment fragment = new ObavijestiDetaljiFragment();
        Bundle args = new Bundle();
        args.putInt(MyApp.ObavijestIDKey, obavijestId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _obavijestId = getArguments().getInt(MyApp.ObavijestIDKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Obavijesti");
        View view = inflater.inflate(R.layout.fragment_obavijesti_detalji, container, false);

        txtNaslovObavijesti = view.findViewById(R.id.txtNaslovObavijesti);
        txtObjavio = view.findViewById(R.id.txtObjavio);
        txtDatumObjave = view.findViewById(R.id.txtDatumObjave);
        txtTekstObavijesti = view.findViewById(R.id.txtTekstObavijesti);

        if (_obavijestId != 0) {
            Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
            IApiService client = retrofit.create(IApiService.class);
            Call<ObavijestVM> call = client.GetObavijestById(String.valueOf(_obavijestId));
            call.enqueue(new Callback<ObavijestVM>() {
                @Override
                public void onResponse(Call<ObavijestVM> call, Response<ObavijestVM> response) {
                    if(response.isSuccessful()){
                        ObavijestVM podaci = response.body();
                        txtNaslovObavijesti.setText(podaci.getNaslov());
                        txtObjavio.setText(podaci.getObjavio());
                        txtDatumObjave.setText(podaci.getDatum());
                        txtTekstObavijesti.setText(podaci.getTekst());
                    }
                    else{
                        Toast.makeText(getActivity(), getString(R.string.ErrMsgFriendly) + ": " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ObavijestVM> call, Throwable t) {
                    Toast.makeText(MyApp.getContext(), getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
