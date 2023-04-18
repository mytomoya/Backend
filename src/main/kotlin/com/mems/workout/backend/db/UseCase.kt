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

    fun get(id: Int): Data? {
        return domain.get(id)
    }

    fun delete(id: Int): Boolean {
        val numDeleted = domain.delete(id)
        return numDeleted > 0
    }

    fun getRecords(offset: Int): List<Data>? {
        return domain.getRecords(offset)
    }

    private fun validate(data: Data): Boolean {

        val time = data.getDataJson().get("time")
        val activities = data.getDataJson().get("activities")
        val yAcc = data.getDataJson().get("yAcc")
        val zAcc = data.getDataJson().get("zAcc")
        val yCorrect = data.getDataJson().get("yCorrect")
        val zCorrect = data.getDataJson().get("zCorrect")

        if (time == null
            || activities == null
            || yAcc == null
            || zAcc == null
            || yCorrect == null
            || zCorrect == null
        ) {
            return false
        }
        if (!time.isArray
            || !activities.isArray
            || !yAcc.isArray
            || !zAcc.isArray
            || !yCorrect.isArray
            || !zCorrect.isArray
        ) {
            return false
        }

        return true
    }
}
