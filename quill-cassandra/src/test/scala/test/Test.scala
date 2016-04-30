package test

import io.getquill._
import io.getquill.naming._

import scala.concurrent.ExecutionContext.Implicits.global

object Quill extends App {

  val db = source(new CassandraAsyncSourceConfig[SnakeCase]("DB"))

  case class WeatherStation(country: String, city: String, stationId: String, entry: Int, value: Int)

  object WeatherStation {

    val getAllByCountry = quote {
      (country: String) =>
        query[WeatherStation].filter(_.country == country)
    }
  }

  val result = db.run(WeatherStation.getAllByCountry)("UK")

  result.onComplete(_ => db.close())
}
