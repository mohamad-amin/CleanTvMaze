package com.mohamadamin.cleantvmaze.database.mapper.show

import com.mohamadamin.cleantvmaze.database.entity.RealmShow
import com.mohamadamin.cleantvmaze.database.entity.toList
import com.mohamadamin.cleantvmaze.domain.entity.*
import easymvp.boundary.DataMapper

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class ShowDatabaseToDomainMapper: DataMapper<RealmShow, Show>() {

    /** @inheritedDoc */
    override fun call(realmShow: RealmShow): Show {

        val realmLinks = realmShow.links
        val realmNetwork = realmShow.network
        val realmCountry = realmShow.network.country
        val realmSchedule = realmShow.schedule
        val realmRating = realmShow.rating
        val realmImage = realmShow.image
        val realmGenres = realmShow.genres

        return Show(
                realmShow.updated,
                ShowLink(ShowUrl(realmLinks.self.href), ShowUrl(realmLinks.previousEpisode.href)),
                Producer(realmNetwork.id,
                        realmNetwork.name, Country(realmCountry.name, realmCountry.code, realmCountry.timezone)),
                Schedule(realmSchedule.time, realmSchedule.days.toList()),
                ShowRating(realmRating.average),
                ShowImageUrl(realmImage.medium, realmImage.original),
                realmGenres.toList(),
                realmShow.id, realmShow.runtime, realmShow.weight, realmShow.url, realmShow.name, realmShow.type,
                realmShow.language, realmShow.status, realmShow.premiered, realmShow.webChannel, realmShow.summary
        )

    }
}