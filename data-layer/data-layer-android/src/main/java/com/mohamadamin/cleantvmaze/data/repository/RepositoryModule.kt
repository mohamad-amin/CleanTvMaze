package com.mohamadamin.cleantvmaze.data.repository

import com.mohamadamin.cleantvmaze.data.repository.show.ShowRepositoryImpl
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
    @Singleton
    fun provideShowRespository(showRepositoryImpl: ShowRepositoryImpl) = showRepositoryImpl

}