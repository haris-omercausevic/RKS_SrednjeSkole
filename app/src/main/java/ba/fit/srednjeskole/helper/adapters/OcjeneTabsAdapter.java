package ba.fit.srednjeskole.helper.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.fragments.OcjeneGenericFragment;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.OcjenaVM;
import ba.fit.srednjeskole.model.UIKorisnik;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OcjeneTabsAdapter extends FragmentPagerAdapter {
    final int TabCount = 3;
    View _rootView;
    private static int _previusTabHeight = 0;
    private List<OcjenaVM> _ocjene;
    private Context context;
    private UIKorisnik korisnik;

    private String tabTitles[] = new String[] { //<--- naslov taba (dugme)
            "Detalji",
            "Agenda",
            "Recenzije"
    };

    public OcjeneTabsAdapter(FragmentManager fm, final Context context, List<OcjenaVM> ocjene) {
        super(fm);
        this.context = context;
        _ocjene = ocjene;

        _rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<List<OcjenaVM>> call = client.GetOcjeneByUcenikRazred(korisnik.KorisnikId, Integer.valueOf(korisnik.razredi.get(korisnik.razredi.size()-1)));

        call.enqueue(new Callback<List<OcjenaVM>>() {
            @Override
            public void onResponse(Call<List<OcjenaVM>> call, Response<List<OcjenaVM>> response) {
                if(response.isSuccessful())
                {
//                    _rootView.findViewById(R.id.OcjeneRazrediTabs).setVisibility(View.VISIBLE);
//                    _rootView.findViewById(R.id.ViewPager).setVisibility(View.VISIBLE);
                    getItem(0);
                }
            }

            @Override
            public void onFailure(Call<List<OcjenaVM>> call, Throwable t) {

            }
        });


    }

    @Override
    public Fragment getItem(int position) {
        //return OcjeneGenericFragment.newInstance(Integer.parseInt(korisnik.razredi.get(position)));
        return null;
    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
