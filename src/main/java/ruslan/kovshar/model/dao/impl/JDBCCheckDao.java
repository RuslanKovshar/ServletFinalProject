package ruslan.kovshar.model.dao.impl;

import ruslan.kovshar.model.dao.CheckDao;
import ruslan.kovshar.model.dao.mapper.BuyerMapper;
import ruslan.kovshar.model.dao.mapper.CheckMapper;
import ruslan.kovshar.model.entity.Check;
import ruslan.kovshar.model.entity.User;
import ruslan.kovshar.model.pagination.Page;
import ruslan.kovshar.view.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCheckDao implements CheckDao {
    private Connection connection;

    public JDBCCheckDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Check entity) {
        try (PreparedStatement ps = connection.prepareStatement(SQL.INSERT_NEW_CHECK, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBigDecimal(1, entity.getTotalPrice());
            ps.setLong(2, entity.getUser().getId());
            ps.setLong(3, entity.getBuyer().getId());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Check findById(Long id) {
        Check check = null;
        try (final PreparedStatement ps = connection.prepareStatement(SQL.SELECT_CHECK_BY_ID_WITH_BUYER)) {
            ps.setLong(1, id);
            final ResultSet resultSet = ps.executeQuery();
            CheckMapper mapper = new CheckMapper();
            BuyerMapper buyerMapper = new BuyerMapper();
            while (resultSet.next()) {
                check = mapper.extractFromResultSet(resultSet);
                check.setBuyer(buyerMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public List<Check> findAll() {
        List<Check> checks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL.SELECT_ALL_CHECK)) {
            final ResultSet resultSet = ps.executeQuery();
            CheckMapper mapper = new CheckMapper();
            while (resultSet.next()) {
                checks.add(mapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    @Override
    public void update(Check entity) {

    }

    @Override
    public void delete(Long id) {
        try (final PreparedStatement ps = connection.prepareStatement(SQL.DELETE_CHECK)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Check> findAllByUser(User user) {
        List<Check> checks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL.SELECT_CHECK_BY_USER)) {
            ps.setLong(1, user.getId());
            final ResultSet resultSet = ps.executeQuery();
            CheckMapper mapper = new CheckMapper();
            BuyerMapper buyerMapper = new BuyerMapper();
            while (resultSet.next()) {
                Check check = mapper.extractFromResultSet(resultSet);
                check.setUser(user);
                check.setBuyer(buyerMapper.extractFromResultSet(resultSet));
                checks.add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    @Override
    public Page<Check> findPageAllByUser(User user, Page pageInfo) {
        Page<Check> page = new Page<>();
        try (PreparedStatement countOfChecksStatement = connection.prepareStatement(SQL.COUNT_OF_CHECKS);
             PreparedStatement checksOnPageStatement = connection.prepareStatement("SELECT * FROM checks " +
                     "JOIN buyer_info ON checks.buyer_id = buyer_info.id " +
                     "WHERE checks.user_id = ? " +
                     "ORDER BY checks.id " + pageInfo.getSortType() +
                     " LIMIT " + (pageInfo.getCurrentPage() - 1) * pageInfo.getMaxResult() + "," + pageInfo.getMaxResult())) {

            countOfChecksStatement.setLong(1, user.getId());
            ResultSet countOfCheckResultSet = countOfChecksStatement.executeQuery();
            if (countOfCheckResultSet.next()) {
                int countOfChecks = countOfCheckResultSet.getInt(1);
                page.setTotalPages((int) Math.ceil((double) countOfChecks / pageInfo.getMaxResult()));
            }

            checksOnPageStatement.setLong(1, user.getId());
            ResultSet checksOnPageResultSet = checksOnPageStatement.executeQuery();
            CheckMapper mapper = new CheckMapper();
            BuyerMapper buyerMapper = new BuyerMapper();
            while (checksOnPageResultSet.next()) {
                Check check = mapper.extractFromResultSet(checksOnPageResultSet);
                check.setUser(user);
                check.setBuyer(buyerMapper.extractFromResultSet(checksOnPageResultSet));
                page.getContent().add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return page;
    }
}