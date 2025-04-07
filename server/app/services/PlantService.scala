package services

import javax.inject._
import models.Plant
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ ExecutionContext, Future }
import java.sql.Timestamp

@Singleton
class PlantService @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private class PlantsTable(tag: Tag) extends Table[Plant](tag, "plants") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def species = column[String]("species")
    def userId = column[Long]("user_id")
    def createdAt = column[Timestamp]("created_at", O.Default(new Timestamp(System.currentTimeMillis())))
    def updatedAt = column[Timestamp]("updated_at", O.Default(new Timestamp(System.currentTimeMillis())))

    def * = (id.?, name, species, userId.?, createdAt.?, updatedAt.?) <> (
      (Plant.apply _).tupled,
      Plant.unapply)
  }

  private val plants = TableQuery[PlantsTable]

  def list(): Future[Seq[Plant]] = {
    db.run(plants.result)
  }

  def get(id: Long): Future[Option[Plant]] = {
    db.run(plants.filter(_.id === id).result.headOption)
  }

  def create(plant: Plant): Future[Plant] = {
    val insertQuery = (plants returning plants.map(_.id)
      into ((plant, id) => plant.copy(id = Some(id)))) += plant
    db.run(insertQuery)
  }

  def update(id: Long, plant: Plant): Future[Option[Plant]] = {
    val updateQuery = plants.filter(_.id === id)
      .map(p => (p.name, p.species, p.userId, p.updatedAt))
      .update((
        plant.name,
        plant.species,
        plant.userId.get,
        new Timestamp(System.currentTimeMillis())))
      .map(rowsAffected => if (rowsAffected > 0) Some(plant.copy(id = Some(id))) else None)
    db.run(updateQuery)
  }

  def delete(id: Long): Future[Boolean] = {
    db.run(plants.filter(_.id === id).delete.map(_ > 0))
  }
}
