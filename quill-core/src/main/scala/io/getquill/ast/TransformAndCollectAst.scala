package io.getquill.ast

class TransformAndCollectAst[T](p: PartialFunction[Ast, (Ast, T)], val state: List[T])
  extends StatefulTransformer[List[T]] {

  override def apply(a: Ast) =
    a match {
      case d if (p.isDefinedAt(d)) =>
        val (ast, value) = p(d)
        (ast, new TransformAndCollectAst(p, state :+ value))
      case other => super.apply(other)
    }
}

object TransformAndCollectAst {
  def apply[T](a: Ast)(p: PartialFunction[Ast, (Ast, T)]) =
    (new TransformAndCollectAst(p, List()).apply(a)) match {
      case (_, transformer) =>
        transformer.state
    }
}
