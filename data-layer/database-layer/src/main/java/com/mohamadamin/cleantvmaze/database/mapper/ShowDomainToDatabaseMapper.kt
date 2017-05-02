package com.mohamadamin.cleantvmaze.database.mapper

import com.mohamadamin.cleantvmaze.database.entity.*
import com.mohamadamin.cleantvmaze.domain.entity.*
import easymvp.boundary.DataMapper

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class ShowDomainToDatabaseMapper: DataMapper<Show, RealmShow>() {

    override fun call(show: Show): RealmShow {

        val links = show.links
        val network = show.network
        val country = show.network.country
        val schedule = show.schedule
        val rating = show.rating
        val image = show.image
        val genres = show.genres

        return RealmShow(
                show.updated,
                RealmShowLink(RealmShowUrl(links.self.href), RealmShowUrl(links.previousEpisode.href)),
                RealmProducer(network.id, network.name,
                        RealmCountry(country.name, country.code, country.timezone)),
                RealmSchedule(schedule.time, RealmStringArray(schedule.days.joinToString(",,,,"))),
                RealmShowRating(rating.average),
                RealmShowImageUrl(image.medium, image.original),
                RealmStringArray(genres.joinToString(",")),
                show.id, show.runtime, show.weight, show.url, show.name, show.type,
                show.language, show.status, show.premiered, show.webChannel, show.summary
        )

    }
}