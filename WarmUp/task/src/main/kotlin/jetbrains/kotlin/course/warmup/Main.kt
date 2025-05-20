package jetbrains.kotlin.course.warmup

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret() : String = "ABCD"

fun countAllMatches(secret: String, guess: String): Int {
    val secretCounts = mutableMapOf<Char, Int>()
    val guessCounts = mutableMapOf<Char, Int>()
    for (char in secret) {
        secretCounts[char] = secretCounts.getOrDefault(char, 0) + 1
    }
    for (char in guess) {
        guessCounts[char] = guessCounts.getOrDefault(char, 0) + 1
    }
    var totalMatches = 0
    for ((char, countInSecret) in secretCounts) {
        val countInGuess = guessCounts.getOrDefault(char, 0)
        totalMatches += minOf(countInSecret, countInGuess)
    }
    return totalMatches
}

fun countPartialMatches(secret: String, guess: String): Int = countAllMatches(secret, guess) - countExactMatches(secret, guess)

fun countExactMatches(secret: String, guess: String): Int {
    var count = 0
    val maxIndex = secret.length
    for (i in 0 until maxIndex) {
        if (secret[i] == guess[i]) {
            count += 1
        }
    }
    return count
}


fun isComplete(secret : String, guess : String) : Boolean {
    return secret == guess
}

fun printRoundResults(secret: String, guess: String) {
    println("Your guess has ${countExactMatches(secret, guess)} full matches and ${countPartialMatches(secret, guess)} partial matches.")
}

fun isWon(complete : Boolean, attempts : Int, maxAttemptsCount: Int) : Boolean {
    return (complete && attempts <= maxAttemptsCount)
}

fun isLost(complete : Boolean, attempts : Int, maxAttemptsCount: Int) : Boolean {
    return (!complete && attempts > maxAttemptsCount)
}

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var attempts = 0
    var complete: Boolean
    do {
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine()
        attempts += 1
        complete = isComplete(secret, guess)
        printRoundResults(secret, guess)
    } while (!complete && attempts <= maxAttemptsCount)

    if (isLost(complete, attempts, maxAttemptsCount)) {
        println("Sorry, you lost! :( My word is $secret")
    }

    if (isWon(complete, attempts, maxAttemptsCount)) {
        println("Congratulations! You guessed it!")
    }
}


fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"
    println(getGameRules(wordLength, maxAttemptsCount, secretExample))
    val secret = generateSecret()
    playGame(secret, wordLength, maxAttemptsCount)
}
