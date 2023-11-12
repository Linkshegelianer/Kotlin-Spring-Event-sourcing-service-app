package project.code.service

import akka.cluster.sharding.typed.javadsl.ClusterSharding
import kotlinx.coroutines.reactor.awaitSingle
import project.code.actor.ClassActor
import project.code.message.StudentActionMessage
import project.code.message.StudentActionType
import project.code.model.AddStudentCommand
import project.code.model.ClassState
import project.code.model.DeleteStudentCommand
import project.code.model.GetAllStudentsCommand
import project.code.props.EventSourcingProperties
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono
import java.time.Duration
import java.util.concurrent.CompletionStage

@Component
class ClassServiceImpl(
    props: EventSourcingProperties,
    private val sharding: ClusterSharding
) : ClassService {

    private val classes = mutableSetOf<String>()
    private val askDuration = Duration.ofSeconds(props.askTimeoutSeconds)

    override fun applyStudentAction(action: StudentActionMessage) = when (action.actionType) {
        StudentActionType.ADD -> action.className.entityRef().tell(AddStudentCommand(action.studentName))
        StudentActionType.DELETE -> action.className.entityRef().tell(DeleteStudentCommand(action.studentName))
    }.also { classes.add(action.className) }

    override suspend fun getCurrentStates(): List<ClassState> = classes
        .map<String, CompletionStage<ClassState>> { className ->
            className.entityRef().ask({ replyTo -> GetAllStudentsCommand(replyTo) }, askDuration)
        }
        .map { it.toCompletableFuture() }
        .map { it.toMono().awaitSingle() }

    private fun String.entityRef() = sharding.entityRefFor(ClassActor.ENTITY_TYPE_KEY, this)

}