package ru.vlasov.developersLife.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vlasov.developersLife.api.NetworkHelper;
import ru.vlasov.developersLife.repositories.MemeRepository;

@Module
public class MemeRepositoryModule {

    @Provides
    @Singleton
    MemeRepository provideMemeRepo(NetworkHelper networkHelper) {
        return new MemeRepository(networkHelper);
    }
}
