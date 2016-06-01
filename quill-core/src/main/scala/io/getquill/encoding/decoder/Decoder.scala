package io.getquill.encoding.decoder

import io.getquill._

trait Decoder[R, T] {
  val expand: Quoted[Query[T] => Query[_]]
  def decode(row: R): T = ???
}
