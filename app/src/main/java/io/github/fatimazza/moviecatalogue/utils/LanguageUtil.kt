package io.github.fatimazza.moviecatalogue.utils

fun String.getFormattedLanguage(): String {
    return if (this.equals("id-ID", false)) {
        "id"
    } else {
        "en"
    }
}
