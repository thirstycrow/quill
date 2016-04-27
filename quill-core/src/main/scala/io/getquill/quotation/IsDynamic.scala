package io.getquill.quotation

import io.getquill.ast._

object IsDynamic {
  def apply(a: Ast) =
    CollectAst[Dynamic](a).nonEmpty
}
