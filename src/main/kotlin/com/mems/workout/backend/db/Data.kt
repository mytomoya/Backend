package com.mems.workout.backend.db

import com.fasterxml.jackson.databind.JsonNode
import java.util.Date

class Data(id: Int, datetime: Date, dataJson: JsonNode) {
    private var id: Int
    private var datetime: Date
    private var dataJson: JsonNode

    init {
        this.id = id
        this.datetime = datetime
        this.dataJson = dataJson
    }
    fun getId(): Int {
        return this.id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getDatetime(): Date {
        return this.datetime
    }

    fun setDatetime(datetime: Date) {
        this.datetime = datetime
    }

    fun getDataJson(): JsonNode {
        return this.dataJson
    }

    fun setDataJson(dataJson: JsonNode) {
        this.dataJson = dataJson
    }
}
