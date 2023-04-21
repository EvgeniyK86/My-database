package org.myfirstdatabase.dao;


import org.myfirstdatabase.entity.Car;
import org.myfirstdatabase.exceptions.DaoException;
import org.myfirstdatabase.utils.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarDao {
    private static final CarDao INSTANCE = new CarDao();

    private static final OwnerDao ownerDao =OwnerDao.getINSTANCE();

    public static CarDao getINSTANCE() {
        return INSTANCE;
    }
    private CarDao(){}

    private static String SAVE_SQL = """
            INSERT INTO car (owner_id,
             model,
             engine_capacity,
             engine_type,
             year)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM car
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT c.id,
            o.owner_name,
            c.model,
            c.engine_capacity,
            c.engine_type,
            c.year
            FROM car c   
            LEFT JOIN owner o on o.id = c.owner_id         
            """;

    private static String UPDATE_SQL = """
            UPDATE car SET
            owner_id = ?,
            model = ?,
            engine_capacity = ?,
            engine_type = ?,
            year = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE c.id = ?
            """;

    private static String FIND_ALL_BY_OWNER = FIND_ALL + """
            WHERE c.owner_id = ?
            """;

    public List<Car> findAllByOwnerId(Long ownerId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_OWNER)) {
            List<Car> cars = new ArrayList<>();
            statement.setLong(1, ownerId);
            var result = statement.executeQuery();
            while (result.next())
                cars.add(buildCar(result));

            return cars;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Car car) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, car.getOwnerId());
            statement.setString(2, car.getModel());
            statement.setDouble(3, car.getEngineCapacity());
            statement.setString(4, car.getEngineType());
            statement.setDate(5, Date.valueOf(car.getYear()));
            statement.setLong(6, car.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Car> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            Car car = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                car = buildCar(result);
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Car buildCar(ResultSet result) throws SQLException {
        return new Car(
                result.getLong("id"),
                result.getLong("owner_id"),
                result.getString("model"),
                result.getDouble("engine_capacity"),
                result.getString("engine_type"),
                result.getDate("year").toLocalDate()
        );
    }

    public List<Car> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Car> cars = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                cars.add(buildCar(result));

            return cars;
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

    public Car save(Car car) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, car.getOwnerId());
            statement.setString(2, car.getModel());
            statement.setDouble(3, car.getEngineCapacity());
            statement.setString(4, car.getEngineType());
            statement.setDate(5, Date.valueOf(car.getYear()));
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                car.setId(generatedKeys.getLong("id"));
            return car;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
