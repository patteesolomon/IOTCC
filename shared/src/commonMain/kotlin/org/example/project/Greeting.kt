package org.example.project

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${this.platform.name}!"
    }
}