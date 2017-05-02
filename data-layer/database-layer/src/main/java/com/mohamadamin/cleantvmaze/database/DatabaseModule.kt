package com.mohamadamin.cleantvmaze.database

import com.mohamadamin.cleantvmaze.database.datasource.RealmShowDataSource
import com.mohamadamin.cleantvmaze.database.mapper.episode.EpisodeDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.season.SeasonDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.show.ShowDatabaseToDomainMapper
import com.mohamadamin.cleantvmaze.database.mapper.show.ShowDomainToDatabaseMapper
import com.mohamadamin.cleantvmaze.domain.Constants
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
@Singleton
@Module
class DatabaseModule {

    @Provides
    fun provideShowDatabaseToDomainMapper(): ShowDatabaseToDomainMapper =
            ShowDatabaseToDomainMapper()

    @Provides
    fun provideShowDomainToDatabaseMapper(): ShowDomainToDatabaseMapper =
            ShowDomainToDatabaseMapper()

    @Provides
    fun provideSeasonDatabaseToDomainMapper(): SeasonDatabaseToDomainMapper =
            SeasonDatabaseToDomainMapper()

    @Provides
    fun provideEpisodeDatabaseToDomainMapper(): EpisodeDatabaseToDomainMapper =
            EpisodeDatabaseToDomainMapper()

    @Provides
    @Named(Constants.DATASOURCE_REALM)
    fun provideRealmShowDataSource(
            showDomainToDB: ShowDomainToDatabaseMapper, showDbToDomain: ShowDatabaseToDomainMapper,
            episodeDbToDomain: EpisodeDatabaseToDomainMapper, seasonDbToDomain: SeasonDatabaseToDomainMapper):
            RetrieveShowDataSource = RealmShowDataSource(
                    showDomainToDB, showDbToDomain, episodeDbToDomain, seasonDbToDomain
            )

}