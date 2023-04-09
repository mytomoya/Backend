package com.mems.workout.backend.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Data @JsonCreator constructor(
    @JsonProperty("y") private var y: Array<Float>,
    @JsonProperty("z") private var z: Array<Float>,
) {
    fun getZ(): Array<Float> {
        return this.z
    }

    fun setZ(z: Array<Float>) {
        this.z = z
    }

    fun getY(): Array<Float> {
        return this.y
    }

    fun setY(y: Array<Float>) {
        this.y = y
    }
}