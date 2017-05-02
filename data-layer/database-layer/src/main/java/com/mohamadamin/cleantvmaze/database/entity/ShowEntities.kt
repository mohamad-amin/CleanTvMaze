package com.mohamadamin.cleantvmaze.database.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
@RealmClass
open class RealmCountry(
        @PrimaryKey open var code: String,
        open var name: String,
        open var timezone: String
): RealmObject()

@RealmClass
open class RealmShowImageUrl(
        @PrimaryKey open var medium: String,
        open var original: String
): RealmObject()

@RealmClass
open class RealmShowUrl(@PrimaryKey open var href: String): RealmObject()

@RealmClass
open class RealmShowLink(
        open var self: RealmShowUrl,
        open var previousEpisode: RealmShowUrl
): RealmObject()

@RealmClass
open class RealmShowRating(open var average: Double) : RealmObject()

@RealmClass
open class RealmPerson(
        @PrimaryKey open var id: Int,
        open var name: String,
        open var image: RealmShowImageUrl
): RealmObject()

@RealmClass
open class RealmPeople(
        open var score: Double,
        open var person: RealmPerson
): RealmObject()

@RealmClass
open class RealmProducer(
        @PrimaryKey open var id: Int,
        open var name: String,
        open var country: RealmCountry
): RealmObject()

@RealmClass
open class RealmSchedule(
        open var time: String,
        open var days: RealmStringArray
): RealmObject()

@RealmClass
open class RealmSeason(
        open var id: Int,
        open var season: Int,
        open var number: Int,
        open var runtime: Int,
        open var url: String,
        open var name: String,
        open var showId: String,
        open var airdate: String,
        open var airtime: String,
        open var airstamp: String,
        open var summary: String,
        open var image: RealmShowImageUrl
): RealmObject()

@RealmClass
open class RealmEpisode(
        open var id: Int,
        open var number: Int,
        open var runtime: Int,
        open var season: Int,
        open var url: String,
        open var name: String,
        open var showId: String,
        open var airdate: String,
        open var airtime: String,
        open var airstamp: String,
        open var summary: String,
        open var image: RealmShowImageUrl
): RealmObject()

@RealmClass
open class RealmShow(
        open var updated: Long,
        open var links: RealmShowLink,
        open var network: RealmProducer,
        open var schedule: RealmSchedule,
        open var rating: RealmShowRating,
        open var image: RealmShowImageUrl,
        open var genres: RealmStringArray,
        @PrimaryKey open var id: Int,
        open var runtime: Int,
        open var weight: Int,
        open var url: String,
        open var name: String,
        open var type: String,
        open var language: String,
        open var status: String,
        open var premiered: String,
        open var webChannel: String,
        open var summary: String
): RealmObject()

