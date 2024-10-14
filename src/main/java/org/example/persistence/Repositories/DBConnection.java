package org.example.persistence.Repositories;

import org.example.api.Dto.CafeDTO;
import org.example.persistence.Entity.CafeEntity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

public class DBConnection {
    static Connection con;
    static DBConnection dbConnection;
    private DBConnection(){

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            con= DriverManager.getConnection(url,username,password);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        if(dbConnection==null){
            return new DBConnection();
        }else {
            return dbConnection;
        }
    }

    public void AddToTable(CafeDTO dto){
        String Query="INSERT INTO cakes VALUES("+dto.getId().toString()+","+'\u0022'+dto.getName().toString()+'\u0022'+","+'\u0022'+dto.getDescription().toString()+'\u0022'+")";
        try(PreparedStatement ps = con.prepareStatement(Query)){

            con.createStatement().executeUpdate(Query);

        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public List<CafeDTO> getFromDataBase(){
        String query="SELECT * FROM cakes";
        Statement stmt=null;
        ResultSet rs=null;

        List<CafeDTO> res=new ArrayList<CafeDTO>();
        try {
             stmt = con.createStatement();
             rs=stmt.executeQuery(query);

            while (rs.next()){
                CafeDTO dto=CafeDTO.builder()
                        .id(Integer.parseInt(rs.getString("id")))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .build();
                    res.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    return res;
    }

}
