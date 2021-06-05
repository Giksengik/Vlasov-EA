package ru.vlasov.developersLife.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.vlasov.developersLife.models.MemePost;

public class NetworkHelper {

    public static final String LATEST_CATEGORY = "latest";
    public static final String HOT_CATEGORY = "hot";
    public static final String TOP_CATEGORY = "top";
    public static final int PAGE_SIZE = 50;

    private JSONPlaceholderAPI jsonPlaceholderAPI;

    public NetworkHelper(Retrofit retrofit){
        jsonPlaceholderAPI = retrofit.create(JSONPlaceholderAPI.class);
    }

    public JSONPlaceholderAPI getPlaceholder(){
        return jsonPlaceholderAPI;
    }

    public static boolean hasConnection(final Context context)
    {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
