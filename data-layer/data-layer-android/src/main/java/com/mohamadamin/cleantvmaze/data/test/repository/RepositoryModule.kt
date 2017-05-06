package com.mohamadamin.cleantvmaze.data.test.repository

import com.mohamadamin.cleantvmaze.data.test.repository.show.ShowRepositoryImpl
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
@Singleton
@Module
class RepositoryModule {

    @Provides
    fun provideShowRepository(showRepositoryImpl: ShowRepositoryImpl): ShowRepository =
            showRepositoryImpl

}