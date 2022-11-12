package api

import net.dv8tion.jda.api.entities.Member

data class Member(var member: Member) {
    private var badNickname = 0
    fun addBadNickname() {
        badNickname++
    }
    fun getBadNickname(): Int {
        return badNickname
    }
    fun resetBadNickname() {
        badNickname = 0
    }
}
