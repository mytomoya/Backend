package com.mems.workout.backend.model

class Value(content: Double) {
    private var content: Double

    init {
        this.content = content
    }

    fun getContent(): Double {
        return this.content
    }

    fun setContent(content: Double) {
        this.content = content
    }
}
