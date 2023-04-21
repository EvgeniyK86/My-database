package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.Owner;
import org.myfirstdatabase.entity.OwnerOrder;
import org.myfirstdatabase.exceptions.DaoException;
import org.myfirstdatabase.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerOrderDao {
    private static final OwnerOrderDao INSTANCE = new OwnerOrderDao();

    public static OwnerOrderDao getINSTANCE() {
        return INSTANCE;
    }

    private OwnerOrderDao() {
    }

    private static String SAVE_SQL = """
            INSERT INTO owner_order (service_id,
            service_station_id,
            car_id)
            VALUES (?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM owner_order
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT o.id,
            s.type_of_service,,
            s1.service_station_name,
            c.model
            FROM owner_order o
            LEFT JOIN services s on s.id = o.services_id
            LEFT JOIN service_station s1 on s1.id = o.service_station_id
            LEFT JOIN car c on c.id = 0.car_id                
            """;

    private static String UPDATE_SQL = """
            UPDATE owner SET
            service_id = ?,
            service_station_id = ?,
            car_id = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE o.id = ?
            """;


    public boolean update(OwnerOrder ownerOrder) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, ownerOrder.getServicesId());
            statement.setLong(2, ownerOrder.getServiceStationId());
            statement.setLong(3, ownerOrder.getCarId());
            statement.setLong(4, ownerOrder.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<OwnerOrder> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            OwnerOrder ownerOrder = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                ownerOrder = buildOwnerOrder(result);
            return Optional.ofNullable(ownerOrder);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private OwnerOrder buildOwnerOrder(ResultSet result) throws SQLException {
        return new OwnerOrder(
                result.getLong("id"),
                result.getLong("service_id"),
                result.getLong("service_station_id"),
                result.getLong("car_id")
        );
    }

    public List<OwnerOrder> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<OwnerOrder> ownerOrders = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                ownerOrders.add(buildOwnerOrder(result));

            return ownerOrders;
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

    public OwnerOrder save(OwnerOrder ownerOrder) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ownerOrder.getServicesId());
            statement.setLong(2, ownerOrder.getServiceStationId());
            statement.setLong(3, ownerOrder.getCarId());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                ownerOrder.setId(generatedKeys.getLong("id"));
            return ownerOrder;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
