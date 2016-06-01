package io

import language.experimental.macros
import io.getquill.sources._

package object getquill {

  def source[T <: Source[_, _]](config: SourceConfig[T]): T = macro Macro.quoteSource[T]
}
