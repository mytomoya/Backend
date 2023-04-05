package com.mems.workout.backend.db

import com.fasterxml.jackson.databind.node.ArrayNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UseCase {
    @Autowired
    private lateinit var domain: Domain

    fun add(data: Data): Boolean {
        if (!validate(data)) {
            return false
        }

        val numUpdated = domain.add(data)
        return numUpdated > 0
    }

    private fun validate(data: Data): Boolean {
        val time = data.getDataJson().get("time")
        val values = data.getDataJson().get("values")

        if (time == null || values == null) {
            return false
        }
        if (!time.isArray || !time.isArray) {
            return false
        }

        val timeArray = time as ArrayNode
        val valuesArray = values as ArrayNode
        if (timeArray.size() != valuesArray.size()) {
            return false
        }
        return true
    }
}
