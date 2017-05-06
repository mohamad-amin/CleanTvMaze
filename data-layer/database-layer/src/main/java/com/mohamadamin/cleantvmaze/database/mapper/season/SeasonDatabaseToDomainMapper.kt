package com.mohamadamin.cleantvmaze.database.mapper.season

import com.mohamadamin.cleantvmaze.database.entity.RealmSeason
import com.mohamadamin.cleantvmaze.domain.entity.Season
import com.mohamadamin.cleantvmaze.domain.entity.ShowImageUrl
import easymvp.boundary.DataMapper

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
class SeasonDatabaseToDomainMapper: DataMapper<RealmSeason, Season>() {

    /** @inheritedDoc */
    override fun call(realmSeason: RealmSeason): Season {
        return Season(realmSeason.id, realmSeason.season, realmSeason.number, realmSeason.runtime,
                realmSeason.url, realmSeason.name, realmSeason.airdate, realmSeason.airtime, realmSeason.airstamp,
                realmSeason.summary, ShowImageUrl(realmSeason.image.medium, realmSeason.image.original))
    }

}