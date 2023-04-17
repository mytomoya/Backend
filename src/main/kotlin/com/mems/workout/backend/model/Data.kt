package com.mems.workout.backend.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class Data @JsonCreator constructor(
    @JsonProperty("time") private var time: Float,
    @JsonProperty("activity") private var activity: Boolean,
    @JsonProperty("y_acc") private var yAcceleration: Float,
    @JsonProperty("z_acc") private var zAcceleration: Float,
    @JsonProperty("y_correct") private var yCorrect: Boolean,
    @JsonProperty("z_correct") private var zCorrect: Boolean,
) {
    fun getActivity(): Boolean {
        return this.activity
    }
    fun setActivity(activity: Boolean) {
        this.activity = activity
    }

    fun getTime(): Float {
        return this.time
    }
    fun setTime(time: Float) {
        this.time = time
    }

    fun getZAcceleration(): Float {
        return this.zAcceleration
    }

    fun setZAcceleration(z: Float) {
        this.zAcceleration = z
    }

    fun getYAcceleration(): Float {
        return this.yAcceleration
    }

    fun setYAcceleration(y: Float) {
        this.yAcceleration = y
    }

    fun getYCorrect(): Boolean {
        return this.yCorrect
    }
    fun setYCorrect(yCorrect: Boolean) {
        this.yCorrect = yCorrect
    }

    fun getZCorrect(): Boolean {
        return this.zCorrect
    }
    fun setZCorrect(zCorrect: Boolean) {
        this.zCorrect = zCorrect
    }
}
