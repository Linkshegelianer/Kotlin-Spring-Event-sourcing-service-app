package project.code.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import project.code.message.ObjectActionMessage
import project.code.service.ClassService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMqListener(
    private val objectMapper: ObjectMapper,
    private val classService: ClassService
) {

    @RabbitListener(queues = ["test_queue"])
    fun listen(messagePayload: String) {
        messagePayload.parseToObjectActionMessage().also { classService.applyObjectAction(it) }
    }

    private fun String.parseToObjectActionMessage(): ObjectActionMessage = objectMapper.readValue(this)

}