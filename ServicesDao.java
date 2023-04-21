package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.Owner;
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

public class ServicesDao {
    private static final ServicesDao INSTANCE = new ServicesDao();

    public static ServicesDao getINSTANCE() {
        return INSTANCE;
    }

    private ServicesDao() {
    }

    private static String SAVE_SQL = """
            INSERT INTO services (type_of_service,
             cost,
             duration)
            VALUES (?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM services
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT s.id,
            s.type_of_service,
            s.cost,
            s.duration
            FROM services s            
            """;

    private static String UPDATE_SQL = """
            UPDATE services SET
            type_of_service = ?,
            cost = ?,
            duration = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE s.id = ?
            """;


    public boolean update(Services services) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, services.getTypeOfService());
            statement.setLong(2, services.getCost());
            statement.setDate(3, (Date) services.getDuration());
            statement.setLong(4, services.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Services> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            Services services = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                services = buildServices(result);
            return Optional.ofNullable(services);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Services buildServices(ResultSet result) throws SQLException {
        return new Services(
                result.getLong("id"),
                result.getString("type_of_service"),
                (int) result.getLong("cost"),
                result.getDate("duration")
        );
    }

    public List<Services> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Services> servicesList = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                servicesList.add(buildServices(result));

            return servicesList;
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

    public Services save(Services services) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, services.getTypeOfService());
            statement.setLong(2, services.getCost());
            statement.setDate(3, (Date) services.getDuration());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                services.setId(generatedKeys.getLong("id"));
            return services;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
