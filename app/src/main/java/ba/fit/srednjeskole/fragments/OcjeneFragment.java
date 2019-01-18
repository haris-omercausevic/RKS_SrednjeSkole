package ba.fit.srednjeskole.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.model.OcjenaVM;
import ba.fit.srednjeskole.model.Storage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OcjeneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OcjeneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private ListView lvOcjene;
    private BaseAdapter adapter;


    public OcjeneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment OcjeneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OcjeneFragment newInstance() {
        OcjeneFragment fragment = new OcjeneFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_ocjene, container, false);

        lvOcjene = view.findViewById(R.id.listViewOcjene);

        popuniPodatke();
        return view;
    }
    private void popuniPodatke() {
        final List<OcjenaVM> podaci = Storage.getOcjene();

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
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

                txtPredmet.setText(x.getPredmet());
                txtProsjekPredmet.setText("Prosjek: " + x.getProsjek());
                txtOcjene.setText("Ocjene: " + x.getOcjene());

                return view;
            }
        };
        lvOcjene.setAdapter(adapter);
    }
}
