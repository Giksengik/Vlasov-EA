package ru.vlasov.developersLife.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.vlasov.developersLife.models.MemeBundle;
import ru.vlasov.developersLife.models.MemePost;

import static ru.vlasov.developersLife.api.NetworkHelper.PAGE_SIZE;

public interface JSONPlaceholderAPI {
    @GET("/{category}/{page}?json=true&pageSize="+PAGE_SIZE+"&types=gif")
    Call<MemeBundle> getGifMemes(@Path("category") String category, @Path("page") int page);

}
