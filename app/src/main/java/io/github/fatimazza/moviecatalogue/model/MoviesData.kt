package io.github.fatimazza.moviecatalogue.model

import io.github.fatimazza.moviecatalogue.R

object MoviesData {

    val listData: ArrayList<Movie>
        get() {
            val list = ArrayList<Movie>()
            for (data in dataHeroes) {
                val movie = Movie()
                movie.title = data[0].toString()
                movie.poster = Integer.valueOf(data[1].toString())
                movie.description = data[2].toString()
                movie.releaseDate = data[3].toString()
                movie.runtime = data[4].toString()
                list.add(movie)
            }
            return list
        }

    private var dataHeroes = arrayOf(
        arrayOf(
            "Avengers: Endgame",
            R.drawable.poster_avengerinfinity,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
            "April 22, 2019",
            "3h 1m"
        ),
        arrayOf(
            "Bird Box",
            R.drawable.poster_birdbox,
            "Five years after an ominous unseen presence drives most of society to suicide, a survivor and her two children make a desperate bid to reach safety.",
            "November 12, 2018",
            "2h 4m"
        ),
        arrayOf(
            "Bumblebee",
            R.drawable.poster_bumblebee,
            "On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken. When Charlie revives him, she quickly learns this is no ordinary yellow VW bug.",
            "December 21, 2018",
            "1h 54m"
        ),
        arrayOf(
            "Once Upon a Deadpool",
            R.drawable.poster_deadpool,
            "A kidnapped Fred Savage is forced to endure Deadpool's PG-13 rendition of Deadpool 2 as a Princess Bride-esque story that's full of magic, wonder & zero F's.",
            "December 11, 2018",
            "1h 57m"
        ),
        arrayOf(
            "How to Train Your Dragon: The Hidden World",
            R.drawable.poster_dragon,
            "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
            "February 22, 2019",
            "1h 44m"
        ),
        arrayOf(
            "Dragon Ball Super: Broly",
            R.drawable.poster_dragonball,
            "Earth is peaceful following the Tournament of Power. Realizing that the universes still hold many more strong people yet to see, Goku spends all his days training to reach even greater heights. Then one day, Goku and Vegeta are faced by a Saiyan called 'Broly' who they've never seen before. The Saiyans were supposed to have been almost completely wiped out in the destruction of Planet Vegeta, so what's this one doing on Earth? This encounter between the three Saiyans who have followed completely different destinies turns into a stupendous battle, with even Frieza (back from Hell) getting caught up in the mix.",
            "December 13, 2018",
            "1h 41m"
        ),
        arrayOf(
            "Mary Poppins Returns",
            R.drawable.poster_marrypopins,
            "In Depression-era London, a now-grown Jane and Michael Banks, along with Michael's three children, are visited by the enigmatic Mary Poppins following a personal loss. Through her unique magical skills, and with the aid of her friend Jack, she helps the family rediscover the joy and wonder missing in their lives.",
            "November 29, 2018",
            "2h 11m"
        ),
        arrayOf(
            "Mortal Engines",
            R.drawable.poster_mortalengine,
            "Many thousands of years in the future, Earth’s cities roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive traction cities, the old London, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the wastelands who will change the course of his life forever.",
            "December 14, 2018",
            "2h 9m"
        ),
        arrayOf(
            "Robin Hood",
            R.drawable.poster_robinhood,
            "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
            "November 21, 2018",
            "1h 56m"
        ),
        arrayOf(
            "Spider-Man: Into the Spider-Verse",
            R.drawable.poster_spiderman,
            "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension.",
            "December 1, 2018",
            "1h 57m"
        )
    )
}
