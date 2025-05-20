package jetbrains.kotlin.course.last.push

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }
            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }
            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun getPatternHeight(pattern: String): Int {
    val lines = pattern.lines()
    return lines.size
}

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
    if (patternRow.length > patternWidth) {
        throw IllegalStateException("Pattern row is longer than pattern width")
    }
    val separatorString = separator.toString()
    val spacesToAdd = patternWidth - patternRow.length
    return patternRow + separatorString.repeat(spacesToAdd)
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    val patternLines = pattern.lines()
    val patternWidth = getPatternWidth(pattern)
    val resultLines = mutableListOf<String>()
    for (level in 0 until height) {
        val linesToUse =
            if (level == 0 || patternLines.size == 1)
                patternLines
            else
                patternLines.drop(1)
        for (line in linesToUse) {
            resultLines += fillPatternRow(line, patternWidth).repeat(width)
        }
    }
    return resultLines.joinToString(newLineSymbol) + newLineSymbol

}

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
    val patternLines   = pattern.lines()
    val patternWidth   = getPatternWidth(pattern)
    val gapBlock       = " ".repeat(patternWidth)
    fun buildRow(startWithPattern: Boolean): List<String> =
        patternLines.map { line ->
            val filled = fillPatternRow(line, patternWidth)
            buildString {
                for (col in 0 until width) {
                    val patternHere =
                        if (width == 1)           true
                        else if (startWithPattern) col % 2 == 0 else col % 2 == 1
                    append(if (patternHere) filled else gapBlock)
                }
            }
        }
    val evenRow = buildRow(startWithPattern = true)
    val oddRow  = if (width == 1) evenRow
    else buildRow(startWithPattern = false)
    val result = buildList {
        for (lvl in 0 until height) addAll(if (lvl % 2 == 0) evenRow else oddRow)
    }
    return result.joinToString(newLineSymbol) + newLineSymbol
}

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    val rows = pattern.lines()
    val repeatedRows = rows.map { row ->
        val filledRow = fillPatternRow(row, patternWidth)
        filledRow.repeat(n)
    }
    return repeatedRows.joinToString(newLineSymbol)
}

fun dropTopLine(image: String, width: Int, patternHeight: Int, patternWidth: Int): String {
    if (patternHeight > 1) {
        val dropAmount = patternWidth * width + newLineSymbol.length
        return image.drop(dropAmount)
    }
    return image
}

fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String {
    val trimmed = pattern.trimIndent()
    return when (generatorName) {
        "canvas" -> canvasGenerator(trimmed, width, height)
        "canvasGaps" -> canvasWithGapsGenerator(trimmed, width, height)
        else -> error("Unknown generator name: $generatorName")
    }
}


fun main() {
    // Uncomment this code on the last step of the game

    val pattern = getPattern()
    val generatorName = chooseGenerator()
    println("Please input the width of the resulting picture:")
    val width = safeReadLine().toInt()
    println("Please input the height of the resulting picture:")
    val height = safeReadLine().toInt()

    println("The pattern:$newLineSymbol${pattern.trimIndent()}")
    println("The generated image:")
    println(applyGenerator(pattern, generatorName, width, height))
}
