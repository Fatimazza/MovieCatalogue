package io.github.fatimazza.moviecatalogue.model

data class LastEpisodeToAir(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: Int,
    val vote_count: Int
)
