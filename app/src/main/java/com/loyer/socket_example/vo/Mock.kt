package com.loyer.socket_example.vo

data class Mock(
    var id: Int,
    var name: String
) {
    val revisedId: String
    get() = id.toString()
}