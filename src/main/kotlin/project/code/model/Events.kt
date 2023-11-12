package project.code.model

import project.code.annotations.CborSerializable

interface ClassEvent : CborSerializable {
    fun applyTo(classState: ClassState): ClassState
}

data class AddObjectEvent(val objectName: String) : ClassEvent {
    override fun applyTo(classState: ClassState) = classState.apply {
        classState.objects.add(objectName)
    }
}

data class DeleteObjectEvent(val objectName: String) : ClassEvent {
    override fun applyTo(classState: ClassState) = classState.apply {
        classState.objects.remove(objectName)
    }
}