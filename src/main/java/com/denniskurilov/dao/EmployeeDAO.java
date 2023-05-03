package com.denniskurilov.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.denniskurilov.models.Employee;

@Component
public class EmployeeDAO {
	private final String URL = "jdbc:oracle:thin:@K";
	private final String USR = "dennis";
	private final String PWD = "***";
	private Connection ora;

	public EmployeeDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ora = DriverManager.getConnection(URL, USR, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Employee> getAll() {
		List<Employee> employee;
		employee = new ArrayList<>();
		try {
			Statement st = ora.createStatement();
			String sql = "SELECT id, first_name, last_name, age, email FROM employee ORDER BY id";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				employee.add(new Employee(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getLong("age"), rs.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	public Employee get(long id) {
		Employee employee = new Employee();
		try {
			String sql = "SELECT id, first_name, last_name, age, email FROM employee WHERE id = ?";
			PreparedStatement pst = ora.prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				employee = new Employee(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getLong("age"), rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	public void save(Employee employee) {
		try {
			String sql = "INSERT INTO employee(first_name, last_name, age, email) VALUES(?, ?, ?, ?)";
			PreparedStatement pst = ora.prepareStatement(sql);
			pst.setString(1, employee.getFirstName());
			pst.setString(2, employee.getLastName());
			pst.setLong(3, employee.getAge());
			pst.setString(4, employee.getEmail());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Employee employee) {
		try {
			String sql = "UPDATE employee SET first_name = ?, last_name = ?, age = ?, email = ? WHERE id = ?";
			PreparedStatement pst = ora.prepareStatement(sql);
			pst.setString(1, employee.getFirstName());
			pst.setString(2, employee.getLastName());
			pst.setLong(3, employee.getAge());
			pst.setString(4, employee.getEmail());
			pst.setLong(5, employee.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long id) {
		try {
			String sql = "DELETE FROM employee WHERE id = ?";
			PreparedStatement pst = ora.prepareStatement(sql);
			pst.setLong(1, id);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
