package project.code.model

import project.code.annotations.CborSerializable

data class ClassState(
    val name: String,
    val objects: MutableSet<String> = mutableSetOf()
): CborSerializable