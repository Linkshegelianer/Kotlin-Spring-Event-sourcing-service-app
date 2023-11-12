package project.code.message

import project.code.annotations.CborSerializable

enum class StudentActionType {
    ADD,
    DELETE
}

data class StudentActionMessage(
    val className: String,
    val studentName: String,
    val actionType: StudentActionType
) : CborSerializable