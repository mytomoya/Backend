package com.mems.workout.backend.model

class Message(content: String) {
    private var content: String

    init {
        this.content = content
    }

    fun getContent(): String {
        return this.content
    }

    fun setContent(content: String) {
        this.content = content
    }
}