package com.oracle.dev.jdbc.micronaut;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import jakarta.transaction.Transactional;

import java.sql.*;
import java.util.Arrays;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import com.oracle.dev.jdbc.micronaut.domain.Thing;
import com.oracle.dev.jdbc.micronaut.repository.ThingRepository;

@Singleton
@Requires(notEnv = "test")
@Requires(property = "flyway.datasources.default.enabled", value = "true")
public class DataPopulator {

  private final ThingRepository thingRepository;
  private final String QUERY = "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = 'ADMIN' AND TABLE_NAME = 'THING'";  
  private DataSource dataSource;
  private boolean baselineOnMigrate;
  private String baselineVersion;

  @Inject
  public DataPopulator(ThingRepository thingRepository, DataSource dataSource,
      @Property(name = "flyway.datasources.default.baseline-on-migrate") boolean baselineOnMigrate,
      @Property(name = "flyway.datasources.default.baseline-version") String baselineVersion)
      throws SQLException {
    this.thingRepository = thingRepository;
    this.dataSource = dataSource;
    this.baselineOnMigrate = baselineOnMigrate;
    this.baselineVersion = baselineVersion;
  }

  @EventListener
  @Transactional
  void init(StartupEvent event) throws SQLException {

    // flyway migration
    migrateDatabase();

    // clear out any existing data
   // thingRepository.deleteAll();

    // add sample data
   // populateTable();

  }

  public void migrateDatabase() throws SQLException {

    // Configure Flyway
    Flyway flyway = Flyway.configure().dataSource(dataSource)
        .baselineOnMigrate(baselineOnMigrate).baselineVersion(baselineVersion)
        .load();

    // Check if the table exists
    boolean tableExists = checkTableExists(dataSource.getConnection());
    // Migrate if the table does not exist
    if (!tableExists) {
      flyway.migrate();
    }
  }

  private boolean checkTableExists(Connection connection) {
    try {
      PreparedStatement stmt = connection.prepareStatement("SELECT 'Hello World!' FROM dual");
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1));
      } return true;
    } catch (SQLException e) {
      throw new RuntimeException("TABLE DOES NOT EXIST", e);
    }
//    try (Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(QUERY)) {
//      return resultSet.next();
//    } catch (SQLException e) {
//      throw new RuntimeException("TABLE DOES NOT EXIST", e);
//    }
  }

  public void populateTable() {
    Thing juarez = new Thing("Juarez");
    Thing kuassi = new Thing("Kuassi");
    Thing paul = new Thing("Paul");
    thingRepository.saveAll(Arrays.asList(juarez, kuassi, paul));
  }

}
