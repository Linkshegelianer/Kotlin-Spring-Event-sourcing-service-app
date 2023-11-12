package project.code.service

import project.code.message.ObjectActionMessage
import project.code.model.ClassState

interface ClassService {
    fun applyObjectAction(action: ObjectActionMessage)
    suspend fun getCurrentStates(): List<ClassState>
}