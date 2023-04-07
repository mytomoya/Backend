package com.mems.workout.backend.db

import org.springframework.dao.DataAccessException

interface Domain {
    @Throws(DataAccessException::class)
    fun add(data: Data): Int

    @Throws(DataAccessException::class, ClassCastException::class)
    fun get(id: Int): Data?

    @Throws(DataAccessException::class, ClassCastException::class)
    fun delete(id: Int): Int
}