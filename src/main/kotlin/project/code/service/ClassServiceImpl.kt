package project.code.service

import akka.cluster.sharding.typed.javadsl.ClusterSharding
import kotlinx.coroutines.reactor.awaitSingle
import project.code.actor.ClassActor
import project.code.message.ObjectActionMessage
import project.code.message.ObjectActionType
import project.code.model.AddObjectCommand
import project.code.model.ClassState
import project.code.model.DeleteObjectCommand
import project.code.model.GetAllObjectsCommand
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

    override fun applyObjectAction(action: ObjectActionMessage) = when (action.actionType) {
        ObjectActionType.ADD -> action.className.entityRef().tell(AddObjectCommand(action.objectName))
        ObjectActionType.DELETE -> action.className.entityRef().tell(DeleteObjectCommand(action.objectName))
    }.also { classes.add(action.className) }

    override suspend fun getCurrentStates(): List<ClassState> = classes
        .map<String, CompletionStage<ClassState>> { className ->
            className.entityRef().ask({ replyTo -> GetAllObjectsCommand(replyTo) }, askDuration)
        }
        .map { it.toCompletableFuture() }
        .map { it.toMono().awaitSingle() }

    private fun String.entityRef() = sharding.entityRefFor(ClassActor.ENTITY_TYPE_KEY, this)

}