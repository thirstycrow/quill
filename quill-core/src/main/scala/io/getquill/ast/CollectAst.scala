package io.getquill.ast

import scala.reflect.ClassTag

class CollectAst[T <: Ast: ClassTag](val state: List[T])
  extends StatefulTransformer[List[T]] {

  override def apply(a: Ast) =
    a match {
      case d: T  => (d, new CollectAst(state :+ d))
      case other => super.apply(other)
    }
}

object CollectAst {
  def apply[T <: Ast: ClassTag](a: Ast) =
    (new CollectAst(List()).apply(a)) match {
      case (_, transformer) =>
        transformer.state
    }
}
