package utils

var prefix = "BOT"
fun log(message: String) {
    println("${parsePrefix("INFO")} $message")
}
fun warn(message: String) {
    println("${parsePrefix("WARN")} $message")
}
fun error(message: String) {
    println("${parsePrefix("ERROR")} $message")
}
private fun parsePrefix(type: String?): String {
    return "[$prefix $type]"
}
