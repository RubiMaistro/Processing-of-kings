package processing.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO class for the {@link GameResult} entity.
 */

@RegisterBeanMapper(GameResult.class)
public interface GameResultDao {

    @SqlUpdate("""
            CREATE TABLE players_XLJKQY (
                name VARCHAR2(20) PRIMARY KEY,
                steps NUMBER(10) NOT NULL,
                solved NUMBER(10) NOT NULL,
                created DATE)
            """)
    void createTable();

    @SqlUpdate("INSERT INTO players_XLJKQY VALUES (:name, :steps, :solved, :created)")
    void insertPlayer(@Bind("name") String name, @Bind("steps") int steps,
                      @Bind("solved") boolean solved, @Bind("created") LocalDate created);

    @SqlUpdate("INSERT INTO players_XLJKQY VALUES (:name, :steps, :solved, :created)")
    void insertPlayerInObj(@BindBean GameResult gameResult);

    @SqlQuery("SELECT name, steps, created FROM players_XLJKQY WHERE solved = 1 ORDER BY steps FETCH FIRST 10 ROWS ONLY")
    List<GameResult> listTopTenPlayerSteps();

}
