package com.mohamadamin.cleantvmaze.data.interactor

import com.mohamadamin.cleantvmaze.domain.interactor.GetShowDetailUseCase
import com.mohamadamin.cleantvmaze.domain.interactor.GetShowsUseCase
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import dagger.Module
import dagger.Provides
import easymvp.executer.PostExecutionThread
import easymvp.executer.UseCaseExecutor
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/3/17.
 */
@Singleton
@Module
class UseCaseModule {

    @Provides
    fun provideGetShowsUseCase(useCaseExecutor: UseCaseExecutor,
                               postExecutionThread: PostExecutionThread, showRepository: ShowRepository) =
            GetShowsUseCase(useCaseExecutor, postExecutionThread, showRepository)

    @Provides
    fun provideGetShowDetailUseCase(useCaseExecutor: UseCaseExecutor,
                                    postExecutionThread: PostExecutionThread, showRepository: ShowRepository) =
            GetShowDetailUseCase(useCaseExecutor, postExecutionThread, showRepository)

}