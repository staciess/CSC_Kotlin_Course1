package jetbrains.kotlin.course.almost.done

fun trimPicture(picture: String): String {
    val lines = picture.lines()
        .dropWhile { it.trim().isEmpty() }
        .dropLastWhile { it.trim().isEmpty() }

    if (lines.isEmpty()) return ""

    val minIndent = lines
        .filter { it.trim().isNotEmpty() }.minOfOrNull { it.indexOfFirst { c -> c != ' ' } } ?: 0

    val result = lines.map {
        if (it.length > minIndent) it.substring(minIndent) else ""
    }

    return result.joinToString("\n")
}

fun applyBordersFilter(picture: String): String {
    val lines = picture.lines()
    val width = getPictureWidth(picture)
    val innerWidth = width + 2
    val borderLine = borderSymbol.toString().repeat(innerWidth + 2)
    val paddedLines = lines.map { line ->
        val padded = line.padEnd(width, separator)
        "$borderSymbol$separator$padded$separator$borderSymbol"
    }
    return (listOf(borderLine) + paddedLines + listOf(borderLine)).joinToString("\n")
}


fun applySquaredFilter(picture: String): String {
    val bordered = applyBordersFilter(picture).dropLast(getPictureWidth(picture) + 5)
    val lines = bordered.lines()
    val topPart = StringBuilder()
    val bottomPart = StringBuilder()
    for (line in lines) {
        val doubledLine = "$line$line"
        topPart.append(doubledLine).append(newLineSymbol)
        bottomPart.append(doubledLine).append(newLineSymbol)
    }
    return topPart.append(bottomPart.toString().trimEnd()).toString() + "\n" + "#".repeat(getPictureWidth(picture)*2 + 8)
}

fun safeReadLine(): String {
    val line = readlnOrNull()
    if (line == null) {
        throw IllegalStateException("No input found")
    }
    return line
}

fun chooseFilter(): String {
    println("Please choose the filter: 'borders' or 'squared'.")
    when (val line = safeReadLine()) {
        "borders", "squared" -> {
            return line
        }
        else -> {
            println("Invalid input. Please try again.")
            return chooseFilter()
        }
    }
}

fun choosePicture(): String {
    val pictures = allPictures()
    println("Please choose a picture. The possible options are: $pictures")
    while (true) {
        val line = safeReadLine()
        val picture = getPictureByName(line)
        if (picture != null) {
            return picture
        } else {
            println("That picture doesn't exist. Please choose one from: $pictures")
        }
    }
}

fun getPicture(): String {
    while (true) {
        println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
        val input = safeReadLine()
        when (input) {
            "yes" -> {
                return choosePicture()
            }
            "no" -> {
                println("Please input a custom picture (note that only single-line images are supported).")
                return safeReadLine()
            }
            else -> {
                println("Please input 'yes' or 'no'.")
            }
        }
    }
}


fun applyFilter(picture: String, filter: String): String {
    val trimmed = trimPicture(picture)
    return when (filter) {
        "borders" -> applyBordersFilter(trimmed)
        "squared" -> applySquaredFilter(trimmed)
        else -> error("Unknown filter: $filter")
    }
}

fun photoshop() {
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image:")
    println(picture)
    val transformedPicture = applyFilter(picture, filter)
    println("The transformed picture:")
    println(transformedPicture)
}



fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}
