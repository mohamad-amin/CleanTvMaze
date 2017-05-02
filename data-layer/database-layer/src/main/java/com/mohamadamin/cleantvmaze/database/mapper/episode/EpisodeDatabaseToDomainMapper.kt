package com.mohamadamin.cleantvmaze.database.mapper.episode

import com.mohamadamin.cleantvmaze.database.entity.RealmEpisode
import com.mohamadamin.cleantvmaze.domain.entity.Episode
import com.mohamadamin.cleantvmaze.domain.entity.ShowImageUrl
import easymvp.boundary.DataMapper

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class EpisodeDatabaseToDomainMapper : DataMapper<RealmEpisode, Episode>() {

    override fun call(episode: RealmEpisode): Episode {
        return Episode(
                episode.id, episode.number, episode.runtime, episode.season, episode.url, episode.name,
                episode.airdate, episode.airtime, episode.airstamp, episode.summary,
                ShowImageUrl(episode.image.medium, episode.image.original)
        )
    }

}