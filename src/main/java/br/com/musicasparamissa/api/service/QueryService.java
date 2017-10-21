package br.com.musicasparamissa.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class QueryService {

    @Autowired
    private DataSource dataSource;

    public Set<Map<String, String>> query(String query) throws SQLException {

        Set<Map<String, String>> response = new HashSet<>();

        Connection conn = dataSource.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(query);;

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        while(resultSet.next()){
            Map<String, String> row = new HashMap<>();
            for(int i=1; i<=metaData.getColumnCount(); i++)
                row.put(metaData.getColumnName(i), resultSet.getString(i));
            response.add(row);
        }

        return response;
    }
}
