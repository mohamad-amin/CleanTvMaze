package com.mohamadamin.cleantvmaze.domain.entity

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
data class Country(val name: String, val code: String, val timezone: String)

data class ShowImageUrl(val medium: String, val original: String)

data class ShowUrl(val href: String)

data class ShowLink(val self: ShowUrl, val previousEpisode: ShowUrl)

data class ShowRating (val average: Double)

data class Person(val id: Int, val name: String, val image: ShowImageUrl)

data class People(var score: Double, var person: Person)

data class Producer(val id: Int, val name: String, val country: Country)

data class Schedule(val time: String, val days: List<String>)

data class Season(val id: Int, val season: Int, val number: Int, 
                  val runtime: Int, val url: String, val name: String, val airdate: String, 
                  val airtime: String, val airstamp: String, val summary: String, val image: ShowImageUrl)

data class Episode(val id: Int, val season: Int, val number: Int,
            val runtime: Int, val url: String, val name: String, val airdate: String,
            val airtime: String, val airstamp: String, val summary: String, val image: ShowImageUrl)

data class Show(val updated: Long, val links: ShowLink, val network: Producer, 
                val schedule: Schedule, val rating: ShowRating, val image: ShowImageUrl, 
                val genres: List<String>, val id: Int, val runtime: Int, val weight: Int, 
                val url: String, val name: String, val type: String, val language: String, 
                val status: String, val premiered: String, val webChannel: String, val summary: String)

