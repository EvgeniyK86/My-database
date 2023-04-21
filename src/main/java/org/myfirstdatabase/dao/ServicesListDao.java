package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.Owner;
import org.myfirstdatabase.entity.ServicesList;
import org.myfirstdatabase.exceptions.DaoException;
import org.myfirstdatabase.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicesListDao {
    private static final ServicesListDao INSTANCE = new ServicesListDao();

    public static ServicesListDao getINSTANCE() {
        return INSTANCE;
    }

    private ServicesListDao() {
    }

    private static String SAVE_SQL = """
            INSERT INTO services_list (service_station_id,
             services_id,
             number_of_services,
             number_of_free)
            VALUES (?, ?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM services_list
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT s2.service_station_name,
            s1.type_of_service,
            s.number_of_services,
            s.number_of_free
            FROM services_list s   
            LEFT JOIN services s1 on s1.id = s.services_id
            LEFT JOIN service_station s2 on s2.id = s.service_station_id                 
            """;

    private static String UPDATE_SQL = """
            UPDATE services_list SET
            service_station_id = ?,
            services_id = ?,
            number_of_services = ?,
            number_of_free = ?
            """;

    private static String FIND_BY_SERVICE = FIND_ALL + """
            WHERE S.services_id = ?
            """;


    public boolean update(ServicesList servicesList) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, servicesList.getServiceStationId());
            statement.setLong(2, servicesList.getServicesId());
            statement.setLong(3, servicesList.getNumberOfServices());
            statement.setLong(4, servicesList.getNumberOfFree());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<ServicesList> findByServiceId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_SERVICE)) {
            ServicesList servicesList = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                servicesList = buildServicesList(result);
            return Optional.ofNullable(servicesList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ServicesList buildServicesList(ResultSet result) throws SQLException {
        return new ServicesList(
                result.getLong("service_station_id"),
                result.getLong("service_id"),
                result.getLong("number_of_services"),
                result.getLong("number_of_free")
        );
    }

    public List<ServicesList> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<ServicesList> servicesLists = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                servicesLists.add(buildServicesList(result));

            return servicesLists;
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

    public ServicesList save(ServicesList servicesList) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, servicesList.getServiceStationId());
            statement.setLong(2, servicesList.getServicesId());
            statement.setLong(3, servicesList.getNumberOfServices());
            statement.setLong(4, servicesList.getNumberOfFree());
            statement.executeUpdate();
                        return servicesList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
