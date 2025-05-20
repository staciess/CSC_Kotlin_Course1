package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts > maxAttemptsCount

fun isComplete(secret: String, currentGuess : String) : Boolean {
    return secret == currentGuess.replace(separator, "")
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    val transformed = currentUserWord.replace(" ", "")
    val newWord = StringBuilder()
    for (i in secret.indices) {
        if (secret[i] == guess) {
            newWord.append(guess)
        } else {
            newWord.append(transformed[i])
        }
    }
    return newWord.toList().joinToString(" ")
}

fun generateSecret() : String {
    return words.random()
}

fun getHiddenSecret(wordLength: Int) : String {
    val hidden : String = "_".repeat(wordLength)
    return hidden.toList().joinToString(" ")
}

fun isCorrectInput(userInput : String) : Boolean {
    if (userInput.length != 1) {println("The length of your guess should be 1! Try again!"); return false}
    if (!userInput[0].isLetter()) {println("You should input only English letters! Try again!"); return false}
    return true
}

fun safeUserInput(): Char {
    var guess: String?
    do {
        println("Please input your guess.")
        guess = safeReadLine()
    } while (guess == null || !isCorrectInput(guess))
    return guess.uppercase()[0]
}


fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    val uppercaseGuess = guess.uppercaseChar()
    if (uppercaseGuess !in secret.uppercase()) {
        println("Sorry, the secret does not contain the symbol: ${guess.uppercase()}. The current word is $currentUserWord")
        return currentUserWord
    }
    val newUserWord = generateNewUserWord(secret, guess, currentUserWord)
    println("Great! This letter is in the word! The current word is $newUserWord")
    return newUserWord
}


fun playGame(secret: String, maxAttemptsCount: Int) {
    val wordLength = secret.length
    println(getGameRules(wordLength, maxAttemptsCount))
    var currentUserWord = getHiddenSecret(wordLength)
    var attempts = 0
    val guessedLetters = mutableSetOf<Char>()
    while (true) {
        if (isLost(isComplete(secret, currentUserWord), attempts, maxAttemptsCount)) {
            println(newLineSymbol + "Sorry, you lost! My word is $secret")
            break
        }
        println(newLineSymbol)
        println("Attempt ${attempts + 1} of $maxAttemptsCount")
        val guess = safeUserInput().uppercaseChar()
        println(newLineSymbol)

        if (guess !in guessedLetters) {
            guessedLetters.add(guess)
            if (guess !in secret.uppercase()) {
                println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
            } else {
                currentUserWord = generateNewUserWord(secret, guess, currentUserWord)
                println("Great! This letter is in the word! The current word is $currentUserWord")
            }
        } else {
            println("You've already guessed $guess. The current word is $currentUserWord")
        }
        attempts++
        if (isComplete(secret, currentUserWord)) {
            println(newLineSymbol + "Congratulations! You guessed it!")
            break
        }
    }
}




fun main() {
    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
}
