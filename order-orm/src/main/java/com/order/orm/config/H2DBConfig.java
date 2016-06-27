package com.order.orm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class H2DBConfig {

    @Autowired
    DataSource                   dataSource;
    
    @Autowired
    DataSourceTransactionManager transactionManager;

    /*public H2DBConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "transactionTemplate")
    public TransactionTemplate getTransactionTemplate() {
        return new TransactionTemplate(transactionManager);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    /*  
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
     */

    @Bean(name = "dataSource")
    public DataSource dataSource() {

        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2) // .H2 or .DERBY
                .addScript("db/sql/create-table-order.sql").addScript("db/sql/insert-order.sql").build();
        return db;
    }

}
