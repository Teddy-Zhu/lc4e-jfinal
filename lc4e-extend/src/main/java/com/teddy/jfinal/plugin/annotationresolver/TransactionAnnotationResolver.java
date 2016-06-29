package com.teddy.jfinal.plugin.annotationresolver;

import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.NestedTransactionHelpException;
import com.jfinal.plugin.activerecord.tx.TxConfig;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.Transaction;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by teddyzhu on 15/12/12.
 */
@CustomAnnotation
public class TransactionAnnotationResolver implements CustomAnnotationPlugin {

    @Override
    public int getOrder() {
        return 11;
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Transaction.class;
    }

    private static Config getConfigWithTxConfig(Method method) {
        TxConfig txConfig = method.getAnnotation(TxConfig.class);
        if (txConfig == null)
            txConfig = method.getDeclaringClass().getAnnotation(TxConfig.class);

        if (txConfig != null) {
            Config config = DbKit.getConfig(txConfig.value());
            if (config == null)
                throw new RuntimeException("Config not found with TxConfig: " + txConfig.value());
            return config;
        }
        return null;
    }

    private int getTransactionLevel(Config config) {
        return config.getTransactionLevel();
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object obj, Method method, boolean[] isHandled) throws Throwable {
        Config config = getConfigWithTxConfig(method);
        if (config == null)
            config = DbKit.getConfig();

        Connection conn = config.getThreadLocalConnection();
        if (conn != null) {    // Nested transaction support
            try {
                if (conn.getTransactionIsolation() < getTransactionLevel(config))
                    conn.setTransactionIsolation(getTransactionLevel(config));
                return resolver.invoke();
            } catch (SQLException e) {
                throw new ActiveRecordException(e);
            }
        }

        Boolean autoCommit = null;
        Object returnValue = null;
        try {
            conn = config.getConnection();
            autoCommit = conn.getAutoCommit();
            config.setThreadLocalConnection(conn);
            conn.setTransactionIsolation(getTransactionLevel(config));    // conn.setTransactionIsolation(transactionLevel);
            conn.setAutoCommit(false);
            returnValue = resolver.invoke();
            conn.commit();
        } catch (NestedTransactionHelpException e) {
            if (conn != null) try {
                conn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Throwable t) {
            if (conn != null) try {
                conn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            throw t instanceof RuntimeException ? (RuntimeException) t : new ActiveRecordException(t);
        } finally {
            try {
                if (conn != null) {
                    if (autoCommit != null)
                        conn.setAutoCommit(autoCommit);
                    conn.close();
                }
            } catch (Throwable t) {
                t.printStackTrace();    // can not throw exception here, otherwise the more important exception in previous catch block can not be thrown
            } finally {
                config.removeThreadLocalConnection();    // prevent memory leak
            }
        }
        return returnValue;
    }
}
