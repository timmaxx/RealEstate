import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.HSQLDialect;

public class HibernateSQLGen {
    public static void main(String[] args) {
        PostgreSQLDialect postgres = new PostgreSQLDialect();
        HSQLDialect hsql = new HSQLDialect();
        // Hibernate сам преобразует HQL/JPA-QL в нужный SQL
    }
}
