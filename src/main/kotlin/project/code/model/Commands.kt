package project.code.model

import akka.actor.typed.ActorRef
import project.code.annotations.CborSerializable

interface ClassCommand: CborSerializable

abstract class ObjectClassCommand(val objectName: String) : ClassCommand

class AddObjectCommand(objectName: String) : ObjectClassCommand(objectName)
class DeleteObjectCommand(objectName: String) : ObjectClassCommand(objectName)
class GetAllObjectsCommand(val replyTo: ActorRef<ClassState>): ClassCommand