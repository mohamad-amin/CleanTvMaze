package com.mohamadamin.cleantvmaze.database.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/2/17.
 */
@RealmClass
public open class RealmCountry(
        @PrimaryKey open var code: String = "",
        open var name: String = "",
        open var timezone: String = ""
): RealmObject()

@RealmClass
public open class RealmShowImageUrl(
        @PrimaryKey open var medium: String = "",
        open var original: String = ""
): RealmObject()

@RealmClass
public open class RealmShowUrl(@PrimaryKey open var href: String = ""): RealmObject()

@RealmClass
public open class RealmShowLink(
        open var self: RealmShowUrl = RealmShowUrl(),
        open var previousEpisode: RealmShowUrl = RealmShowUrl()
): RealmObject()

@RealmClass
public open class RealmShowRating(open var average: Double = 0.0) : RealmObject()

@RealmClass
public open class RealmPerson(
        @PrimaryKey open var id: Int = 0,
        open var name: String = "",
        open var image: RealmShowImageUrl = RealmShowImageUrl()
): RealmObject()

@RealmClass
public open class RealmPeople(
        open var score: Double = 0.0,
        open var person: RealmPerson = RealmPerson()
): RealmObject()

@RealmClass
public open class RealmProducer(
        @PrimaryKey open var id: Int = 0,
        open var name: String = "",
        open var country: RealmCountry = RealmCountry()
): RealmObject()

@RealmClass
public open class RealmSchedule(
        open var time: String = "",
        open var days: RealmStringArray = RealmStringArray()
): RealmObject()

@RealmClass
public open class RealmSeason(
        open var id: Int = 0,
        open var season: Int = 0,
        open var number: Int = 0,
        open var runtime: Int = 0,
        open var url: String = "",
        open var name: String = "",
        open var showId: String = "",
        open var airdate: String = "",
        open var airtime: String = "",
        open var airstamp: String = "",
        open var summary: String = "",
        open var image: RealmShowImageUrl = RealmShowImageUrl()
): RealmObject()

@RealmClass
public open class RealmEpisode(
        open var id: Int = 0,
        open var number: Int = 0,
        open var runtime: Int = 0,
        open var season: Int = 0,
        open var url: String = "",
        open var name: String = "",
        open var showId: String = "",
        open var airdate: String = "",
        open var airtime: String = "",
        open var airstamp: String = "",
        open var summary: String = "",
        open var image: RealmShowImageUrl = RealmShowImageUrl()
): RealmObject()

@RealmClass
public open class RealmShow(
        open var updated: Long = 0,
        open var links: RealmShowLink = RealmShowLink(),
        open var network: RealmProducer = RealmProducer(),
        open var schedule: RealmSchedule = RealmSchedule(),
        open var rating: RealmShowRating = RealmShowRating(),
        open var image: RealmShowImageUrl = RealmShowImageUrl(),
        open var genres: RealmStringArray = RealmStringArray(),
        @PrimaryKey open var id: Int = 0,
        open var runtime: Int = 0,
        open var weight: Int = 0,
        open var url: String = "",
        open var name: String = "",
        open var type: String = "",
        open var language: String = "",
        open var status: String = "",
        open var premiered: String = "",
        open var webChannel: String = "",
        open var summary: String = ""
): RealmObject()

