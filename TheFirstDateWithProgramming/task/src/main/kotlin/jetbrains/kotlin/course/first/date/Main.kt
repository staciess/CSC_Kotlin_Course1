package jetbrains.kotlin.course.first.date

fun main() {
    println("Hello! I will ask you several questions.\n" +
            "Please answer all of them and be honest with me!")
    println("What is TROTEN?")
    val firstUserAnswer: String? = readlnOrNull()
    println("How did you spend your graduation?")
    val secondUserAnswer: String? = readlnOrNull()
    println("Why does a spider need eight legs?")
    val thirdUserAnswer: String? = readlnOrNull()
    println("Now let's have fun!")
    println(firstQuestion)
    println(firstUserAnswer)
    println(secondQuestion)
    println(secondUserAnswer)
    println(thirdQuestion)
    println(thirdUserAnswer)
}
