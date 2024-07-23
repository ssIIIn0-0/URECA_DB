package app.book.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class MyBatisConfig {
	public SqlSessionFactory getSqlSessionFactory() {
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_basic");
		dataSource.setUsername("root");
		dataSource.setPassword("31215");
		
		Configuration configuration = new Configuration();
		configuration.setEnvironment(
				new org.apache.ibatis.mapping.Environment("development", new JdbcTransactionFactory(), dataSource));
		
		configuration.addMapper(app.book.dao.BookDao.class);
		
		return new SqlSessionFactoryBuilder().build(configuration);
	}
}
