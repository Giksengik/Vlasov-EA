package ru.vlasov.developersLife.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import ru.vlasov.developersLife.R;
import ru.vlasov.developersLife.api.NetworkHelper;
import ru.vlasov.developersLife.databinding.FragmentMainBinding;
import ru.vlasov.developersLife.models.MemePost;
import ru.vlasov.developersLife.repositories.MemeRepository;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;
    private MemeRepository.PageEnum pageEnum;
    private boolean hasImageDownloadingError = false;
    int index = 1;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        switch(index) {
            default :
                pageEnum = MemeRepository.PageEnum.LATEST;
                pageViewModel.getCurrentLastMemeContent().observe(getViewLifecycleOwner(), memePost -> {
                    if(memePost == null){
                        showConnectionErrorLayout();
                    }else
                        setGifInfo(memePost);

                });
                pageViewModel.loadMemes(pageEnum, hasImageDownloadingError);
                break;
            case 2:
                pageEnum = MemeRepository.PageEnum.BEST;
                pageViewModel.getCurrentBestMemeContent().observe(getViewLifecycleOwner(), memePost -> {
                    if(memePost == null){
                        showConnectionErrorLayout();
                    }else
                        setGifInfo(memePost);

                });
                pageViewModel.loadMemes(pageEnum, hasImageDownloadingError);
                break;
            case 3:
                pageEnum = MemeRepository.PageEnum.HOT;
                pageViewModel.getCurrentHotMemeContent().observe(getViewLifecycleOwner(), memePost -> {
                    if(memePost == null){
                        showConnectionErrorLayout();
                    }else
                        setGifInfo(memePost);

                });
                pageViewModel.loadMemes(pageEnum, hasImageDownloadingError);
        }

        binding.nextButton.setOnClickListener(v -> {
            pageViewModel.showNextContent(pageEnum);
            binding.progressBar.setVisibility(View.VISIBLE);
        });
        binding.previousButton.setOnClickListener(v -> {
            pageViewModel.showPrevContent(pageEnum);
            binding.progressBar.setVisibility(View.VISIBLE);
        });
        binding.buttonRetryConnect.setOnClickListener(v -> {
            if(NetworkHelper.hasConnection(getContext())){
                pageViewModel.loadMemes(pageEnum, hasImageDownloadingError);
                showGifInfoLayout();
            }
        });

        return binding.getRoot();
    }

    private void setGifInfo(MemePost memePost) {
        if (memePost.getGifURL() != null) {
            Glide.with(getContext())
                    .asGif()
                    .load(memePost.getGifURL())
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            if(NetworkHelper.hasConnection(getContext())){
                                return true;
                            }else {
                                hasImageDownloadingError = true;
                                showConnectionErrorLayout();
                                return false;
                            }
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progressBar.setVisibility(View.GONE);
                            hasImageDownloadingError = false;
                            return false;
                        }
                    })
                    .centerCrop()
                    .timeout(30000)
                    .into(binding.gifPlaceholder);
            binding.gifDescription.setText(memePost.getDescription());
        }
        else {
            showEndOfContentNotification();
        }
    }
    public void showConnectionErrorLayout() {
        binding.gifInfoLayout.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.VISIBLE);
        binding.previousButton.setVisibility(View.GONE);
        binding.nextButton.setVisibility(View.GONE);
        binding.endOfContentLayout.setVisibility(View.GONE);
    }

    public void showGifInfoLayout() {
        binding.gifInfoLayout.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.errorLayout.setVisibility(View.GONE);
        binding.previousButton.setVisibility(View.VISIBLE);
        binding.nextButton.setVisibility(View.VISIBLE);
        binding.endOfContentLayout.setVisibility(View.GONE);
    }
    public void showEndOfContentNotification() {
        binding.gifInfoLayout.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.GONE);
        binding.previousButton.setVisibility(View.VISIBLE);
        binding.nextButton.setVisibility(View.VISIBLE);
        binding.endOfContentLayout.setVisibility(View.VISIBLE);
    }
}