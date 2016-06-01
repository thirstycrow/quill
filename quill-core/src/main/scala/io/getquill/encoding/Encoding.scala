package io.getquill.encoding

import io.getquill._
import language.experimental.macros

trait Encoding[R, S] {

  trait Decoder[T] {
    val expand: Quoted[Query[T] => Query[_]]
    def decode(row: R): T = ???
  }
  
  implicit def derivateDecoder[T]: Decoder[T] {} = macro DecoderMacro.derivateDecoder[T]
}

class DecoderMacro(val c: scala.reflect.macros.whitebox.Context) {
  import c.universe._

  def derivateDecoder[T](implicit t: WeakTypeTag[T]): Tree = {
    if (t.tpe.typeSymbol.asClass.isCaseClass)
      caseClassDecoder[T]
    else
      EmptyTree
  }
  
  private def caseClassDecoder[T](implicit t: WeakTypeTag[T]) =
    q"""
      import io.getquill._
      new ${c.prefix}.Decoder[$t] {
        override val expand =
          quote((q: Query[Person]) => q.map(p => tupleN(p.name, p.age)))
      }
    """
}
