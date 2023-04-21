package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.ServiceStationAddress;
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

public class ServiceStationAddressDao {
    private static final ServiceStationAddressDao INSTANCE = new ServiceStationAddressDao();

    public static ServiceStationAddressDao getINSTANCE() {
        return INSTANCE;
    }
    private ServiceStationAddressDao(){

    }

    private static String SAVE_SQL = """
            INSERT INTO service_station_address (service_station_id,
             city,
             street,
             house,
             email,
             phone_number)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM service_station_address
            WHERE service_station_id = ?
            """;

    private static String FIND_ALL = """
            SELECT s.service_station_id,
            s.city,
            s.street,
            s.house,
            s.email,
            s.phone_number
            FROM service_station_address s            
            """;

    private static String UPDATE_SQL = """
            UPDATE service_station_address SET
            service_station_id = ?
            city = ?,
            street = ?,
            house = ?,
            email = ?,
            phone_numbern = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE s.id = ?
            """;



    public boolean update(ServiceStationAddress serviceStationAddress) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, serviceStationAddress.getServiceStationId());
            statement.setString(2, serviceStationAddress.getCity());
            statement.setString(3, serviceStationAddress.getStreet());
            statement.setString(4, serviceStationAddress.getHouse());
            statement.setString(5, serviceStationAddress.getEmail());
            statement.setString(6, serviceStationAddress.getPhoneNumber());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<ServiceStationAddress> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            ServiceStationAddress serviceStationAddress = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                serviceStationAddress = buildServiceStationAddress(result);
            return Optional.ofNullable(serviceStationAddress);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ServiceStationAddress buildServiceStationAddress(ResultSet result) throws SQLException {
        return new ServiceStationAddress(
                result.getLong("service_station_id"),
                result.getString("city"),
                result.getString("street"),
                result.getString("house"),
                result.getString("email"),
                result.getString("phone_number")
        );
    }

    public List<ServiceStationAddress> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<ServiceStationAddress> serviceStationAddresses = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                serviceStationAddresses.add(buildServiceStationAddress(result));

            return serviceStationAddresses;
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

    public ServiceStationAddress save(ServiceStationAddress serviceStationAddress) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, serviceStationAddress.getServiceStationId());
            statement.setString(2, serviceStationAddress.getCity());
            statement.setString(3, serviceStationAddress.getStreet());
            statement.setString(4, serviceStationAddress.getHouse());
            statement.setString(5, serviceStationAddress.getEmail());
            statement.setString(6, serviceStationAddress.getPhoneNumber());
            statement.executeUpdate();
            return serviceStationAddress;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
