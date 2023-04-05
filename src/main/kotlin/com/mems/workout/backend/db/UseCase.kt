package com.mems.workout.backend.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UseCase {
    @Autowired
    private lateinit var domain: Domain

    fun add(data: Data): Boolean {
        val numUpdated = domain.add(data)
        return numUpdated > 0
    }
}