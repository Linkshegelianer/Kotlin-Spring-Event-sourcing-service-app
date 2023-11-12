package project.code.service

import project.code.message.StudentActionMessage
import project.code.model.ClassState

interface ClassService {
    fun applyStudentAction(action: StudentActionMessage)
    suspend fun getCurrentStates(): List<ClassState>
}