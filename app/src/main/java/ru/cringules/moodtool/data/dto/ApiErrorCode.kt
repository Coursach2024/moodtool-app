package ru.cringules.moodtool.data.dto

enum class ApiErrorCode(val code: Int) {
    UNKNOWN(0),
    INVALID_QUERY(1),
    ENTITY_DOES_NOT_EXIST(2),
    ACCESS_DENIED(3),
    INCORRECT_CREDENTIALS(4),
    INCORRECT_PARAMETER(5),
    ENTITY_ALREADY_EXISTS(6),
    EXTERNAL_SERVER_ERROR(7),
    EXTERNAL_SERVICE_IS_NOT_RESPONDING(8);

    companion object {
        fun fromCode(code: Int) = ApiErrorCode.values().first { it.code == code }
    }
}