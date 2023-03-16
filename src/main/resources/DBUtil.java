package com.ru.mag.db.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	private PreparedStatement studentsForCourse = null;

	private PreparedStatement createStudent = null;
	
	private static final String GET_STUDENTS_FOR_COURSE_QUERY = "SELECT * FROM student WHERE course = ?";

	private static final String INSERT_STUDENT_QUERY = "INSERT INTO student VALUES(?, ?, ?, ?, ?)";
	
	private Connection cachedConnection = null;
	
	private static final DBUtil instance = new DBUtil();
	
	private DBUtil()
	{
		
	}
	
	public static DBUtil getInstance(){
		return instance;
	}
	
	private Connection getConnection()
	{
		try {
			if (cachedConnection == null ||
					cachedConnection.isClosed() ||
					!cachedConnection.isValid(10)){
				System.out.println("Attempting to get a new connection to DB!");
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				cachedConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@172.16.251.135:1521:orcl", "c##ex22_milen", "123456");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.cachedConnection;
	}
	
	private PreparedStatement getStudentsStatement(){
		if (studentsForCourse == null){
			try {
				studentsForCourse = getConnection().prepareStatement(GET_STUDENTS_FOR_COURSE_QUERY);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return studentsForCourse;
	}
	
	public ResultSet getStudentsForCourse(int course){
		ResultSet result = null;
		try {
			PreparedStatement stmt = getStudentsStatement();
			stmt.setInt(1, course);
			result = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
