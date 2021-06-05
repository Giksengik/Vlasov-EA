package ru.vlasov.developersLife.ui.main;

import android.graphics.pdf.PdfDocument;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ru.vlasov.developersLife.di.DaggerMemeRepositoryComponent;
import ru.vlasov.developersLife.di.MemeRepositoryComponent;
import ru.vlasov.developersLife.models.MemePost;
import ru.vlasov.developersLife.repositories.MemeRepository;

public class PageViewModel extends ViewModel {

    @Inject
    MemeRepository repository;

    public PageViewModel() {
        MemeRepositoryComponent component = DaggerMemeRepositoryComponent.create();
        component.inject(this);
    }

    public void loadMemes(MemeRepository.PageEnum pageEnum, boolean hasImageLoadingProblem) {
        repository.loadMemes(pageEnum, hasImageLoadingProblem);
    }

    public LiveData<MemePost> getCurrentLastMemeContent() {
        return repository.getCurrentLastMemesContent();
    }

    public LiveData<MemePost> getCurrentHotMemeContent() {
        return repository.getCurrentHotMemesContent();
    }

    public LiveData<MemePost> getCurrentBestMemeContent() {
        return repository.getCurrentBestMemesContent();
    }

    public void showNextContent(MemeRepository.PageEnum pageEnum) {
        repository.showNextGif(pageEnum);
    }

    public void showPrevContent(MemeRepository.PageEnum pageEnum){
        repository.showPrevGif(pageEnum);
    }
}