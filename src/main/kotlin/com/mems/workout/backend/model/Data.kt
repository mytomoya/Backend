package com.mems.workout.backend.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Data @JsonCreator constructor(
    @JsonProperty("time") private var time: Float,
    @JsonProperty("y") private var y: Float,
    @JsonProperty("z") private var z: Float,
) {
    fun getTime(): Float {
        return this.time
    }
    fun setTime(time: Float) {
        this.time = time
    }

    fun getZ(): Float {
        return this.z
    }

    fun setZ(z: Float) {
        this.z = z
    }

    fun getY(): Float {
        return this.y
    }

    fun setY(y: Float) {
        this.y = y
    }
}
