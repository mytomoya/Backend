package com.mems.workout.backend.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository("Domain")
class Repository: Domain {
    @Autowired
    private lateinit var jdbc: JdbcTemplate

    override fun add(data: Data): Int {
        return jdbc.update(
            "INSERT INTO log (datetime, data_json)"
                    + " VALUES(?, ?)",
            data.getDatetime(), data.getDataJson().toString(),
        )
    }
}