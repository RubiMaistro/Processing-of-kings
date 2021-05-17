package processing.results;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Date;

public class Example {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:testdb");
        jdbi.installPlugin(new SqlObjectPlugin());
        Handle handle = jdbi.open();
        GameResultDao gameResultDao = handle.attach(GameResultDao.class);

        GameResult gameResult = GameResult.builder()
                .name("Bob")
                .steps(10)
                .solved(1)
                .created(new Date())
                .build();
        gameResultDao.createTable();
        gameResultDao.insertPlayerInObj(gameResult);
        System.out.println(gameResult);
        System.out.println(gameResultDao.listTopTenPlayerSteps());

    }
}
