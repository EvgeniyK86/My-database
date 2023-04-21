package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.ServiceStation;
import org.myfirstdatabase.entity.Services;
import org.myfirstdatabase.exceptions.DaoException;
import org.myfirstdatabase.utils.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceStationDao {
    private static final ServiceStationDao INSTANCE = new ServiceStationDao();

    public static ServiceStationDao getINSTANCE() {
        return INSTANCE;
    }
    private ServiceStationDao(){}

    private static String SAVE_SQL = """
            INSERT INTO service_station (service_station_name)
            VALUES (?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM service_station
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT s.id,
            s.service_station_name
            FROM service_station s            
            """;

    private static String UPDATE_SQL = """
            UPDATE service_station SET
            service_station_name = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE s.id = ?
            """;


    public boolean update(ServiceStation serviceStation) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, serviceStation.getServiceStationName());
            statement.setLong(2, serviceStation.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<ServiceStation> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            ServiceStation serviceStation = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                serviceStation = buildServiceStation(result);
            return Optional.ofNullable(serviceStation);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ServiceStation buildServiceStation(ResultSet result) throws SQLException {
        return new ServiceStation(
                result.getLong("id"),
                result.getString("service_station_name")
        );
    }

    public List<ServiceStation> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<ServiceStation> serviceStations = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                serviceStations.add(buildServiceStation(result));

            return serviceStations;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public ServiceStation save(ServiceStation serviceStation) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, serviceStation.getServiceStationName());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                serviceStation.setId(generatedKeys.getLong("id"));
            return serviceStation;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
