package io.getquill.quotation

import scala.reflect.macros.whitebox.Context

object Bindings {
  def apply(c: Context)(quoted: c.Tree): Map[c.Symbol, c.Tree] = {
    import c.universe._
    quoted.tpe
      .member(TermName("bindings"))
      .typeSignature.decls.collect {
        case m: MethodSymbol if (m.isGetter) =>
          m -> q"$quoted.bindings.$m"
      }.toMap
  }
}
