package com.loyer.socket_example.vo

data class Mock(
    val id: Int,
    val name: String
) {
    val revisedId: String
    get() = id.toString()
}