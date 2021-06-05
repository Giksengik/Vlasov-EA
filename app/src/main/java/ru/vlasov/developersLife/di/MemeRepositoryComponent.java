package ru.vlasov.developersLife.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.vlasov.developersLife.MainActivity;
import ru.vlasov.developersLife.repositories.MemeRepository;
import ru.vlasov.developersLife.ui.main.PageViewModel;

@Component( modules = {NetworkModule.class, MemeRepositoryModule.class})
@Singleton
public interface MemeRepositoryComponent {
    MemeRepository getRepo();
    void inject(PageViewModel viewModel);
}
