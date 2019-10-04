package io.github.fatimazza.moviecatalogue.model

data class Movie(
    var title: String = "",
    var poster: Int = 0,
    var description: String = "",
    var releaseDate: String = "",
    var runtime: String = ""
)
