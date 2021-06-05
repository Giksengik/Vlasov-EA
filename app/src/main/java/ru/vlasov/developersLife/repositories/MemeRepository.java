package ru.vlasov.developersLife.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vlasov.developersLife.api.NetworkHelper;
import ru.vlasov.developersLife.di.App;
import ru.vlasov.developersLife.models.MemeBundle;
import ru.vlasov.developersLife.models.MemePost;

public class MemeRepository {

    private NetworkHelper mNetworkHelper;

    int lastCounter = 0;
    int bestCounter = 0;
    int hotCounter = 0;


    private MutableLiveData<MemePost> currentLastMemesContent;
    private List<MemePost> lastMemesPosts;

    private MutableLiveData<MemePost> currentHotMemesContent;
    private List<MemePost> hotMemesPosts;

    private MutableLiveData<MemePost> currentBestMemesContent;
    private List<MemePost> bestMemesPosts;

    public  enum PageEnum {
        HOT, LATEST, BEST
    }

    public MemeRepository(NetworkHelper networkHelper) {
        this.mNetworkHelper = networkHelper;

        currentLastMemesContent = new MutableLiveData<>();
        lastMemesPosts =  new ArrayList<>();

        currentBestMemesContent = new MutableLiveData<>();
        bestMemesPosts =  new ArrayList<>();

        currentHotMemesContent = new MutableLiveData<>();
        hotMemesPosts =  new ArrayList<>();
    }



    public LiveData<MemePost> getCurrentLastMemesContent() {
        return currentLastMemesContent;
    }

    public LiveData<MemePost> getCurrentHotMemesContent() {
        return currentHotMemesContent;
    }

    public LiveData<MemePost> getCurrentBestMemesContent() {
        return currentBestMemesContent;
    }

    public void loadMemes(PageEnum pageEnum, boolean hasImageLoadingProblem) {
        switch(pageEnum){
            case LATEST:
                loadLastMemes(hasImageLoadingProblem);
                break;
            case BEST:
                loadBestMemes(hasImageLoadingProblem);
                break;
            case HOT:
                loadHotMemes(hasImageLoadingProblem);
                break;
        }
    }
    public void loadLastMemes(boolean hasImageLoadingProblem) {
        if(!hasImageLoadingProblem) {
            int page = (lastMemesPosts.size() > 0) ? (lastCounter / NetworkHelper.PAGE_SIZE) + 1 : 0;
            mNetworkHelper.getPlaceholder()
                    .getGifMemes(NetworkHelper.LATEST_CATEGORY, page)
                    .enqueue(new Callback<MemeBundle>() {
                        @Override
                        public void onResponse(Call<MemeBundle> call, Response<MemeBundle> response) {
                            if (response.body() != null) {
                                if (response.body().getPosts().size() > 0) {
                                    lastMemesPosts.addAll(response.body().getPosts());
                                    currentLastMemesContent.setValue(response.body().getPosts().get(0));
                                    if (lastCounter != 0) lastCounter++;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MemeBundle> call, Throwable t) {
                            if(NetworkHelper.hasConnection(App.getInstance())){
                                loadLastMemes(false);
                            }
                            else currentLastMemesContent.setValue(null);
                        }
                    });
        }else
            currentLastMemesContent.setValue(currentLastMemesContent.getValue()); //trigger LiveData
    }
    public void loadBestMemes(boolean hasImageLoadingProblem) {
        if(!hasImageLoadingProblem) {
            mNetworkHelper.getPlaceholder()
                    .getGifMemes(NetworkHelper.TOP_CATEGORY, bestCounter / NetworkHelper.PAGE_SIZE)
                    .enqueue(new Callback<MemeBundle>() {
                        @Override
                        public void onResponse(Call<MemeBundle> call, Response<MemeBundle> response) {
                            if (response.body().getPosts().size() > 0) {
                                bestMemesPosts.addAll(response.body().getPosts());
                                currentBestMemesContent.setValue(response.body().getPosts().get(0));
                                if (bestCounter != 0) bestCounter++;
                            }

                        }

                        @Override
                        public void onFailure(Call<MemeBundle> call, Throwable t) {
                            if(NetworkHelper.hasConnection(App.getInstance())){
                                loadBestMemes(false);
                            }
                            else currentBestMemesContent.setValue(null);
                        }
                    });
        }
        else currentBestMemesContent.setValue(currentBestMemesContent.getValue());
    }

    public void loadHotMemes(boolean hasImageLoadingProblem) {
        if(!hasImageLoadingProblem) {
            mNetworkHelper.getPlaceholder()
                    .getGifMemes(NetworkHelper.HOT_CATEGORY, hotCounter / NetworkHelper.PAGE_SIZE)
                    .enqueue(new Callback<MemeBundle>() {
                        @Override
                        public void onResponse(Call<MemeBundle> call, Response<MemeBundle> response) {
                            if (response.body().getPosts().size() > 0) {
                                hotMemesPosts.addAll(response.body().getPosts());
                                currentHotMemesContent.setValue(response.body().getPosts().get(0));
                                if (hotCounter != 0) hotCounter++;
                            }

                        }

                        @Override
                        public void onFailure(Call<MemeBundle> call, Throwable t) {
                            if (NetworkHelper.hasConnection(App.getInstance())) {
                                loadHotMemes(false);
                            } else currentHotMemesContent.setValue(null);
                        }
                    });
        }
        else  currentHotMemesContent.setValue(currentHotMemesContent.getValue());
    }

    public void showNextGif(PageEnum pageEnum) {
        switch(pageEnum) {
            case LATEST:
                showNextGifFromLatest();
                break;
            case HOT:
                showNextGifFromHot();
                break;
            case BEST:
                showNextGifFromBest();
                break;
        }
    }
    public void showPrevGif(PageEnum pageEnum) {
        switch (pageEnum){
            case LATEST:
                showPreGifFromLatest();
            case HOT:
                showPreGifFromHot();
                break;
            case BEST:
                showPreGifFromBest();
                break;
        }
    }

    private void showNextGifFromLatest(){
        if(lastMemesPosts.size() > lastCounter + 1){
            lastCounter++;
            currentLastMemesContent.setValue(lastMemesPosts.get(lastCounter));
        }else {
            loadLastMemes(false);
        }
    }
    private void showPreGifFromLatest(){
        if(lastCounter >= 1){
            lastCounter-- ;
            currentLastMemesContent.setValue(lastMemesPosts.get(lastCounter));
        }
    }

    private void showNextGifFromHot(){
        if(hotMemesPosts.size() > hotCounter + 1){
            hotCounter++;
            currentHotMemesContent.setValue(hotMemesPosts.get(hotCounter));
        }else {
            loadHotMemes(false);
        }
    }
    private void showPreGifFromHot(){
        if(hotCounter >= 1) {
            hotCounter--;
            currentHotMemesContent.setValue(hotMemesPosts.get(hotCounter));
        }
    }

    private void showNextGifFromBest(){
        if(bestMemesPosts.size() > bestCounter + 1){
            bestCounter++;
            currentBestMemesContent.setValue(bestMemesPosts.get(bestCounter));
        }else {
            loadBestMemes(false);
        }
    }
    private void showPreGifFromBest(){
        if(bestCounter >= 1){
            bestCounter --;
            currentBestMemesContent.setValue(bestMemesPosts.get(bestCounter));
        }
    }


}
