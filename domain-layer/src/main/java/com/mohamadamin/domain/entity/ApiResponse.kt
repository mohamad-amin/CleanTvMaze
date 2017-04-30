package com.mohamadamin.domain.entity

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 */
data class Country(var name: String, var code: String, var timezone: String)

data class ShowImageUrl(var medium: String, var original: String)

data class ShowUrl(var href: String)

data class ShowLink(var self: ShowUrl, var previousEpisode: ShowUrl)

data class ShowRating (var average: Double)

data class Person(var id: Int, var name: String, var image: ShowImageUrl)

data class Producer(var id: Int, var name: String, var country: Country)

data class Schedule(var time: String, var days: List<String>)

data class Season(var id: Int, var season: Int, var number: Int, 
                  var runtime: Int, var url: String, var name: String, var airdate: String, 
                  var airtime: String, var airstamp: String, var summary: String, var image: ShowImageUrl)

data class Episode(var id: Int, var season: Int, var number: Int,
            var runtime: Int, var url: String, var name: String, var airdate: String,
            var airtime: String, var airstamp: String, var summary: String, var image: ShowImageUrl)

data class Show(var updated: Long, var links: ShowLink, var network: Producer, 
                var schedule: Schedule, var rating: ShowRating, var image: ShowImageUrl, 
                var genres: List<String>, var id: Int, var runtime: Int, var weight: Int, 
                var url: String, var name: String, var type: String, var language: String, 
                var status: String, var premiered: String, var webChannel: String, var summary: String)

