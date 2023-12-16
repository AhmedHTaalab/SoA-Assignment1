package com.SoA.Fox;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.SoA.Fox.Settings.XML_FILE_PATH;


@Service
public class EmployeeService {

    public List<Employee> readEmployeesFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = null;

        try {
            // Read JSON file and convert to List<Employee>
            employees = objectMapper.readValue(new File(XML_FILE_PATH), new TypeReference<List<Employee>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees != null ? employees : new ArrayList<>();
    }




    public void addEmployee(String filePath, Employee newEmployee) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(filePath);

            List<Employee> employees;

            // If the file exists, read existing data and append to it
            if (file.exists()) {
                employees = objectMapper.readValue(file, getEmployeeListType(objectMapper));
            } else {
                employees = new ArrayList<>(); // Create a new list if the file doesn't exist
            }

            employees.add(newEmployee); // Add the new employee to the list

            // Write the updated list back to the file
            objectMapper.writeValue(file, employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private CollectionType getEmployeeListType(ObjectMapper mapper) {
        TypeFactory typeFactory = mapper.getTypeFactory();
        return typeFactory.constructCollectionType(ArrayList.class, Employee.class);
    }


    public void deleteEmployeeByID(String id, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File not found");
                return;
            }

            // Read the existing data from the file
            List<Employee> employees = objectMapper.readValue(file, getEmployeeListType(objectMapper));

            // Remove the employee with the specified ID
            Iterator<Employee> iterator = employees.iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                if (String.valueOf(employee.getEmployeeID()).equals(id)) {
                    iterator.remove();
                    break; // Stop after deleting the first occurrence of the ID
                }
            }

            // Write the updated list back to the file
            objectMapper.writeValue(file, employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeEmployeeToJsonFile(Employee updatedEmployee, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(filePath);

            List<Employee> employees;

            // If the file exists, read existing data
            if (file.exists()) {
                employees = objectMapper.readValue(file, getEmployeeListType(objectMapper));
            } else {
                employees = new ArrayList<>(); // Create a new list if the file doesn't exist
            }

            // Find and update the existing employee in the list (if present)
            boolean found = false;
            for (Employee emp : employees) {
                if (emp.getEmployeeID() == updatedEmployee.getEmployeeID()) {
                    emp.setDesignation(updatedEmployee.getDesignation());
                    found = true;
                    break;
                }
            }

            // If the employee doesn't exist in the list, add it
            if (!found) {
                employees.add(updatedEmployee);
            }

            // Write the updated list back to the file
            objectMapper.writeValue(file, employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateEmployee(int employeeID, String newDesignation, int gradeIncrement) {
        List<Employee> employees = readEmployeesFromJsonFile();

        for (Employee employee : employees) {
            if (employee.getEmployeeID() == employeeID) {
                employee.setDesignation(newDesignation);
                // Assuming the Employee class has a 'grade' attribute

                writeEmployeeToJsonFile(employee, XML_FILE_PATH); // Write the updated employee back to the file
                break;
            }
        }
    }


}



