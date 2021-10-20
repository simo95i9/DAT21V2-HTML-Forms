package shit.htmlforms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import shit.htmlforms.models.User;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Consumer;

@Service
public class UserService implements AbstractService<User>, RowMapper<User> {
    @Autowired
    private JdbcTemplate jdbc;

    @PostConstruct
    public void init() {
        try {
            jdbc.execute(
            """
                drop table if exists users;
                """
            );

            jdbc.execute(
            """
                create table users
                   (
                   id int auto_increment,
                   names varchar(255) not null,
                   email varchar(255) not null,
                   money decimal(25) not null,
                   constraint users_id_uindex unique (id)
                   );
                """
            );

            jdbc.execute(
            """
                alter table users
                add primary key (id);
                """
            );
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void test(Consumer<JdbcTemplate> function) {
        function.accept(jdbc);
    }

    @Override
    public boolean create(User user) {
        System.out.printf("Called UserService.create() with parameter %s%n", user);
        String query = "INSERT INTO users (names, email, money) VALUES (?, ?, ?)";

        int rowsAffected = 0;

        try {
            rowsAffected = jdbc.update(query, user.getNames(), user.getEmail(), user.getMoney());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rowsAffected == 1;
    }

    @Override
    public boolean update(String id, User user) {
        System.out.printf("Called UserService.update() with parameter id='%s' and %s%n", id, user);
        return false;
    }

    @Override
    public boolean delete(String id) {
        System.out.printf("Called UserService.delete() with parameter id='%s'%n", id);
        return false;
    }

    @Override
    public Collection<User> get() {
        System.out.printf("Called UserService.get()%n");
        String sql = "SELECT * FROM users;";
        return jdbc.query(sql, this);
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("names"),
                rs.getString("email"),
                rs.getDouble("money")
        );
    }
}
