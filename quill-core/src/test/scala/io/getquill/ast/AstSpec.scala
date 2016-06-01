package io.getquill.ast

import io.getquill.Spec

class AstSpec extends Spec {

  import source._

  "overrides toString to ease debugging" in {
    val q =
      quote {
        query[TestEntity].filter(t => t.s == "test")
      }
    q.ast.toString mustEqual """query[TestEntity].filter(t => t.s == "test")"""
  }
}
