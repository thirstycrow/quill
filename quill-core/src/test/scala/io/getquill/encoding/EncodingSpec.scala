package io.getquill.encoding

import io.getquill._
import language.reflectiveCalls
object Subject extends Encoding[List[Any], List[Any]]

class EncodingSpec {
  import Subject._

  def test[T](implicit d: Decoder[T]) = ???

  case class Person(name: String, age: String)

  derivateDecoder[Person].expand.ast
  
  val d = (quote(query[Person]))
}
