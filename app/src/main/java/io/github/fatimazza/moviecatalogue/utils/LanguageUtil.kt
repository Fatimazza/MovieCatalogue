package io.github.fatimazza.moviecatalogue.utils

fun String.getFormattedLanguage(): String {
    return if (this.equals("in-ID", false)) {
        "id"
    } else {
        "en"
    }
}
