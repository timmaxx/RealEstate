import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class CalciteSQLTranslation {
    public static void main(String[] args) throws SqlParseException {
        String sql = "SELECT * FROM users WHERE id = 1";
        SqlParser parser = SqlParser.create(sql);
        SqlNode sqlNode = parser.parseQuery();

        // Можно преобразовать в другой диалект
        System.out.println("Parsed SQL: " + sqlNode);
    }
}
