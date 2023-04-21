package org.myfirstdatabase.dao;

import org.myfirstdatabase.entity.Car;
import org.myfirstdatabase.entity.Owner;
import org.myfirstdatabase.entity.OwnerStatus;
import org.myfirstdatabase.exceptions.DaoException;
import org.myfirstdatabase.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerDao {
    private static final OwnerDao INSTANCE = new OwnerDao();

    public static OwnerDao getINSTANCE() {
        return INSTANCE;
    }

    private OwnerDao() {
    }

    private static String SAVE_SQL = """
            INSERT INTO owner (passport_no,
             owner_name,
             email,
             phone_number,
             status)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static String DELETE_SQL = """
            DELETE FROM owner
            WHERE id = ?
            """;

    private static String FIND_ALL = """
            SELECT o.id,
            o.passport_no,
            o.owner_name,
            o.email,
            o.phone_number,
            o.status
            FROM owner o            
            """;

    private static String UPDATE_SQL = """
            UPDATE owner SET
            passport_no = ?,
            owner_name = ?,
            email = ?,
            phone_number = ?,
            status = ?
            """;

    private static String FIND_BY_ID = FIND_ALL + """
            WHERE o.id = ?
            """;


    public boolean update(Owner owner) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, owner.getPassportNo());
            statement.setString(2, owner.getOwnerName());
            statement.setString(3, owner.getEmail());
            statement.setString(4, owner.getPhoneNumber());
            statement.setString(5, owner.getStatus().toString());
            statement.setLong(6, owner.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Owner> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID)) {
            Owner owner = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                owner = buildOwner(result);
            return Optional.ofNullable(owner);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Owner buildOwner(ResultSet result) throws SQLException {
        return new Owner(
                result.getLong("id"),
                result.getString("passport_no"),
                result.getString("owner_name"),
                result.getString("email"),
                result.getString("phone_number"),
                (OwnerStatus) result.getObject("status")
        );
    }

    public List<Owner> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL)) {
            List<Owner> owners = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                owners.add(buildOwner(result));

            return owners;
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

    public Owner save(Owner owner) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, owner.getPassportNo());
            statement.setString(2, owner.getOwnerName());
            statement.setString(3, owner.getEmail());
            statement.setString(4, owner.getPhoneNumber());
            statement.setString(5, owner.getStatus().toString());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                owner.setId(generatedKeys.getLong("id"));
            return owner;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
