package com.mems.workout.backend.db

import org.springframework.dao.DataAccessException

interface Domain {
    @Throws(DataAccessException::class)
    fun add(data: Data): Int
}
