package api

import net.dv8tion.jda.api.entities.Role

val members = mutableListOf<Member>()
var roleOfMember: Role? = null

fun addMember(member: Member) {
    members.add(member)
}

fun removeMember(member: Member) {
    members.remove(member)
}

fun getMember(member: Member): Member? {
    return members.find { it.member == member.member }
}

fun getMember(member: net.dv8tion.jda.api.entities.Member): Member {
    val result = members.find { it.member == member }
    if (result == null) {
        val newMember = Member(member)
        addMember(newMember)
        return newMember
    }
    return result
}

fun defineRole(role: Role) {
    roleOfMember = role
}

fun getRole(): Role? {
    return roleOfMember
}
