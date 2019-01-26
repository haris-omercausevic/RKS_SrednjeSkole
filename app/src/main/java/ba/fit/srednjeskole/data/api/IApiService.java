package ba.fit.srednjeskole.data.api;

import java.util.List;

import ba.fit.srednjeskole.model.LoginVM;
import ba.fit.srednjeskole.model.MaterijalOcjenaVM;
import ba.fit.srednjeskole.model.MaterijalVM;
import ba.fit.srednjeskole.model.ObavijestVM;
import ba.fit.srednjeskole.model.OcjenaVM;
import ba.fit.srednjeskole.model.PredmetVM;
import ba.fit.srednjeskole.model.UIKorisnik;
import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiService {
    //predmeti
    @GET("Predmeti/ByRazred/{razredId}")
    Call<List<PredmetVM>> GetPredmetiByRazred(@Path("razredId") int razredId);

    //Obavijesti
    @GET("Obavijesti/Pretraga/{naziv}")
    Call<List<ObavijestVM>> GetObavijestiByNaziv(@Query("naziv") String naziv);

    @GET("Obavijesti/ById/{obavijestid}")
    Call<ObavijestVM> GetObavijestById(@Path("obavijestid") String obavijestid);

    //Materijali
    @GET("Materijali/ById/{materijalId}")
    Call<MaterijalVM> GetMaterijalById(@Path("materijalId") int materijalId);

    @GET("Materijali/ByPredmetId/{predmetId}")
    Call<List<MaterijalVM>> GetMaterijaliByPredmet(@Path("predmetId") int predmetId);

    @GET("Materijali/PreporuciMaterijale/{korisnikId}/{razred}")
    Call<List<MaterijalVM>> PreporuciMaterijale(@Path("korisnikId") int korisnikId, @Path("razred") int razred);

    //MaterijaliOcjene
    @GET("MaterijaliOcjene/IsOcijenjeno/{materijalId}/{korisnikId}")
    boolean IsOcijenjeno(@Path("materijalId") int materijalId, @Path("korisnikId") int korisnikId);

    @POST("MaterijaliOcjene/")
    Call<ResponseBody> OcijeniMaterijal(@Body MaterijalOcjenaVM materijalOcjenaVM);

    //Autentifikacija
    @POST("Autentifikacija/login")
    Call<UIKorisnik> Login(@Body LoginVM LoginVM);

    @POST("Autentifikacija/loginwithtoken")
    Call<UIKorisnik> LoginWithToken(@Body String authToken);

    @GET("Autentifikacija/ResetPassword/{username}")
    Completable ResetPassword (@Query("username") String username);

    @GET("Autentifikacija/logout/{token}")
    Completable Logout (@Query("token") String token);

    @GET("MaterijaliOcjene/IsOcijenjeno/{materijalId}")
    Call<String> GetMaterijalOcjenaIsOcijenjeno(@Path("materijalId") int materijalId);

    @GET("UceniciOcjene/ByUceniciRazredi/{korisnikId}/{razredId}")
    Call<List<OcjenaVM>> GetOcjeneByUcenikRazred(@Path("korisnikId")int korisnikId,@Path("razredId") int razredId);

}
