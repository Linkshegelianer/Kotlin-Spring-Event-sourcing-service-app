package project.code.model

import project.code.annotations.CborSerializable

data class ClassState(
    val name: String,
    val students: MutableSet<String> = mutableSetOf()
): CborSerializable