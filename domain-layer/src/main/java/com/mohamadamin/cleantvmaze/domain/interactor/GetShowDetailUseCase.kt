package com.mohamadamin.cleantvmaze.domain.interactor

import com.mohamadamin.cleantvmaze.domain.entity.Show
import com.mohamadamin.cleantvmaze.domain.repository.ShowRepository
import easymvp.executer.PostExecutionThread
import easymvp.executer.UseCaseExecutor
import easymvp.usecase.ObservableUseCase
import rx.Observable

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
class GetShowDetailUseCase(useCaseExecutor: UseCaseExecutor,
                           postThread: PostExecutionThread, private val showRepository: ShowRepository) :
        ObservableUseCase<Show, String>(useCaseExecutor, postThread) {

    override fun interact(showId: String?): Observable<Show> {
        return showRepository.getShow(showId!!)
    }

}
