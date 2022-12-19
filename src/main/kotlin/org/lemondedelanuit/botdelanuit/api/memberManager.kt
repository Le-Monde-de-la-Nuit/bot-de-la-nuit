package org.lemondedelanuit.botdelanuit.api

import net.dv8tion.jda.api.entities.Role

val members = mutableListOf<Member>()
var roleOfMember: Role? = null

/**
 * Add the member from the list of members
 * @param member the member to add
 */
fun addMember(member: Member) {
    members.add(member)
}

/**
 * Remove the member from the list of members
 * @param member the member to remove
 */
fun removeMember(member: Member) {
    members.remove(member)
}

/**
 * Get the member from the list of members
 *
 * If the member doesn't exist, it will be created
 * @param member the member to get
 * @return the member
 */
fun getMember(member: net.dv8tion.jda.api.entities.Member): Member {
    val result = members.find { it.member == member }
    if (result == null) {
        val newMember = Member(member)
        addMember(newMember)
        return newMember
    }
    return result
}
