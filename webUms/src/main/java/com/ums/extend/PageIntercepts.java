package com.ums.extend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.ums.common.util.StringUtil;
import com.ums.extend.dialect.SQLdailect;
import com.ums.extend.dialect.imple.MySqlDialect;
import com.ums.extend.page.RowBonds;
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class}) })
public class PageIntercepts implements Interceptor{

	SQLdailect sqlDialect  = new MySqlDialect();
	
//	private Logger log = LoggerFactory.getLogger(PageIntercepts.class);
	
	private static final String DEFAULT_PAGE_KEYWORD =".*Page$";

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	
	public Object intercept(Invocation ivk) throws Throwable {
		// 得到mybatis 当前执行者
		StatementHandler statementHandler = (StatementHandler) ivk.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,DEFAULT_OBJECT_WRAPPER_FACTORY);
		
		// 读取property在mybatis settings文件内配置
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        // String pageKeyword = StringUtils._default(configuration.getVariables().getProperty("pageKeyWord"), DEFAULT_PAGE_KEYWORD);
        String pageKeyword = DEFAULT_PAGE_KEYWORD;
        // 判断是否需要分页查询
        MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
        if (mappedStatement.getId().matches(pageKeyword)) {
        	RowBonds rowBounds = (RowBonds) metaStatementHandler.getValue("delegate.rowBounds");
        	if (rowBounds == RowBounds.DEFAULT) {
        		throw new NullPointerException(StringUtil.append(mappedStatement.getId(),"缺失RowBounds对象,请检查"));
			}
        	BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			Object parameterObject = boundSql.getParameterObject();
			if (parameterObject == null) {
			    throw new NullPointerException(StringUtil.append(mappedStatement.getId(),"parameterObject is null!"));
			}
        	String sql = boundSql.getSql();
        	String totalsql = sqlDialect.getTotalSQL(sql);
        	rowBounds.setTotal(this.getToatal(totalsql,(Connection)ivk.getArgs()[0], mappedStatement, boundSql));
        	// 重写sql
            String pageSql = sqlDialect.getPageSQL(sql, rowBounds.getOffset(), rowBounds.getLimit());
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		}
		return ivk.proceed();
	}

	private int getToatal(final String _sql, Connection connection,MappedStatement mappedStatement, BoundSql boundSql) {
		// 创建一个 mybatis SQL存储器
		BoundSql _boundSql = new BoundSql(mappedStatement.getConfiguration(),_sql, boundSql.getParameterMappings(),boundSql.getParameterObject());
		// 创建数据库执行者
		PreparedStatement preparedStatement = null;
		// jdbc ResultSet 对象
		ResultSet rs = null;
		int total = 0;
		try {
			preparedStatement = connection.prepareStatement(_sql);
			// 设置mybatis ParameterHandler 参数装载
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, _boundSql.getParameterObject(), _boundSql);
			parameterHandler.setParameters(preparedStatement);
			// 执行查询
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
//			log.error(e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			try {
				rs.close();
				preparedStatement.clearBatch();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler || target instanceof ResultSetHandler) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }
	}

	public void setProperties(Properties arg0) {
		
	}
}
