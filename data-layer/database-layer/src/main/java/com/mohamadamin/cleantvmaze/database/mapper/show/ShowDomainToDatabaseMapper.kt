package com.mohamadamin.cleantvmaze.database.mapper.show

import com.mohamadamin.cleantvmaze.database.entity.*
import com.mohamadamin.cleantvmaze.domain.entity.*

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class ShowDomainToDatabaseMapper: easymvp.boundary.DataMapper<Show, RealmShow>() {

    /** @inheritedDoc */
    override fun call(show: com.mohamadamin.cleantvmaze.domain.entity.Show): com.mohamadamin.cleantvmaze.database.entity.RealmShow {

        val links = show.links
        val network = show.network
        val country = show.network.country
        val schedule = show.schedule
        val rating = show.rating
        val image = show.image
        val genres = show.genres

        return com.mohamadamin.cleantvmaze.database.entity.RealmShow(
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