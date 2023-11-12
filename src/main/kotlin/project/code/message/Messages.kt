package project.code.message

import project.code.annotations.CborSerializable

enum class ObjectActionType {
    ADD,
    DELETE
}

data class ObjectActionMessage(
    val className: String,
    val objectName: String,
    val actionType: ObjectActionType
) : CborSerializable