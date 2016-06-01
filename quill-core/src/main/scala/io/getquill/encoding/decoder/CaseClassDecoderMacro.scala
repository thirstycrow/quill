package io.getquill.encoding.decoder

import scala.reflect.macros.whitebox.Context
import io.getquill.util.Messages._

trait CaseClassDecoderMacro {
  val c: Context
  import c.universe._

  def caseClassDecoder[T](implicit t: WeakTypeTag[T]) = {
    caseClassConstructor(t.tpe).paramLists
    q"""
      import io.getquill._
      new ${c.prefix}.Decoder[$t] {
        override val expand =
          quote((q: Query[Person]) => q.map(p => tupleN(p.name, p.age)))
      }
    """
  }

  private def caseClassConstructor(t: Type) =
    t.members.collect {
      case m: MethodSymbol if (m.isPrimaryConstructor) => m
    }.headOption.getOrElse(
      c.fail(s"Can't find the primary constructor of `$t`." +
        "Please file a bug report, a case class should always provide a default constructor."))

}
