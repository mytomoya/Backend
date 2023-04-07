package com.mems.workout.backend.db

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Repository("Domain")
class Repository : Domain {
    @Autowired
    private lateinit var jdbc: JdbcTemplate

    override fun add(data: Data): Int {
        return jdbc.update(
            "INSERT INTO log (datetime, data_json)"
                    + " VALUES(?, ?)",
            data.getDatetime(), data.getDataJson().toString(),
        )
    }

    override fun get(id: Int): Data? {
        try {
            // When 0 records match,
            // it throws `EmptyResultDataAccessException`
            val map = jdbc.queryForMap(
                "SELECT * FROM log" +
                        " WHERE id = ?",
                id
            )

            val date = convertToDate(map["datetime"].toString())
            val objectMapper = ObjectMapper()
            val dataJson = objectMapper.readTree(map["data_json"].toString())

            if (date == null || dataJson == null) {
                return null
            }

            return Data(id, date, dataJson)
        } catch (exception: Exception) {
            println("[Exception] $exception")
            return null
        }
    }

    fun convertToDate(dateString: String): Date? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDateTime = LocalDateTime.parse(dateString, formatter)
        val jstZoneId = ZoneId.of("Asia/Tokyo")
        val zonedDateTime = ZonedDateTime.of(localDateTime, jstZoneId)
        val instant = zonedDateTime.toInstant()

        return Date.from(instant)
    }

    override fun delete(id: Int): Int {
        return jdbc.update(
            "DELETE FROM log WHERE id = ?",
            id,
        )
    }
}