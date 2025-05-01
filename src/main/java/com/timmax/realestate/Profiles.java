package com.timmax.realestate;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    //  Get DB profile depending on DB driver in classpath
    public static String getActiveDbProfile() {
        String result = "";
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            result = POSTGRES_DB;
        }
        if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            if (!result.isEmpty()) {
                throw new IllegalStateException("Probably more than one maven profile is enabled for the DB");
            }
            result = HSQL_DB;
        }

        if (!result.isEmpty()) {
            return result;
        }
        throw new IllegalStateException("Could not find DB driver (probably no maven profile enabled for the DB)");
/*
        //  Вариант topjava34:
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }

*/
    }
}
