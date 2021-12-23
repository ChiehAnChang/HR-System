package main.UsesCases;

import main.Entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeList implements Iterable<Employee>, Serializable, IEmployeeList {

    // === Instance Variables ===

    private final List<Employee> EmployeeList;
    private int idCounter;

    /* === Representation Invariants ===
     * The EmployeeList should have at least one Employee inside one company having the top authority level, which is 0.
     */

    public EmployeeList(){
        this.EmployeeList = new ArrayList<>();
        this.idCounter = 0;
    }


    /**
     * This method will check the type of employees i.e. whether he is full-time or part-time employee,
     * and add a new employee information into the EmployeeList.
     *
     * @param department the department of the employee.
     * @param wage the  wage of the employee.
     * @param position the position of the employee, usually it is "N".
     * @param level the level of the employee. With smaller numbers, the employee has higher authority level.
     * @param status the status of employee, F represent full-time employee, P represent part-time employee
     * @param name the name of employee
     */
    @Override
    public void addEmployee(String department, String wage, String position, String level, String status, String name) {
        if (status.equals("F")) {
            Employee employee = new FullTimeEmployee(department, position, wage, Integer.parseInt(level), name.concat(String.valueOf(this.idCounter)));
            this.EmployeeList.add(employee);
        } else if (status.equals("P") && position.equals("N")) {
            Employee employee = new PartTimeEmployee(department, wage, Integer.parseInt(level), name.concat(String.valueOf(this.idCounter)) );
            this.EmployeeList.add(employee);
        }
        this.idCounter += 1;
    }



    /**
     * This method will find the Employee from the EmployeeList.
     *
     * @param userID the employee's id that needs to be found.
     * @return the Employee found.
     */
    @Override
    public Employee getEmployee(String userID) {
        for (Employee e: this.EmployeeList) {
            if (e.getID().equals(userID)) {
                return e;
            }
        }
        return null;
    }


    /**
     * This method will find the Employee from the EmployeeList and delete the Employee.
     *
     * @param id the employee's id that needs to be deleted.
     */
    @Override
    public void deleteEmployee(String id) {
        Employee employee = this.getEmployee(id);
        this.EmployeeList.remove(employee);
    }


    /**
     * This method will get the size of the EmployeeList, which is the total number of employees inside one company.
     *
     * @return the int of the size of the EmployeeList.
     */
    @Override
    public int getSize(){
        return this.EmployeeList.size();
    }


    // === Data ===
    @Override
    public void initialized(){
        Employee admin = new FullTimeEmployee("N/A", "N/A","0", 0, "Admin");
        this.EmployeeList.add(admin);
    }

    // @ Credit by UTM CSC 207 Lecture code. We have made some modification after our research.
    @Override
    public void readDataFromFile() throws IOException, ClassNotFoundException {
        String filePath = new File("").getAbsolutePath();
        String targetFile = filePath.concat("/src/Data/UserEmployeeData.ser");
        InputStream file = new FileInputStream(targetFile);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        EmployeeList EmployeeFile = (EmployeeList) input.readObject();
        input.close();
        for(Employee employee: EmployeeFile){
            this.EmployeeList.add(employee);
            this.idCounter ++;
        }
    }

    // @ Credit by UTM CSC 207 Lecture code. We have made some modification after our research.
    @Override
    public void writeDataToFile() throws IOException {
        String filePath = new File("").getAbsolutePath();
        String targetFIle =  filePath.concat("/src/Data/UserEmployeeData.ser");
        OutputStream file = new FileOutputStream(targetFIle);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(this);
        output.close();
    }

    // ===============================

    // === Iterator Design Pattern ===
    // @ Credit by UTSG CSC 207 Lecture code.
    @Override
    public Iterator<Employee> iterator() {
        return new EmployeeListIterator();
    }




    public class EmployeeListIterator implements Iterator<Employee>{

        private int curr_index = 0;

        @Override
        public boolean hasNext() {
            return curr_index < EmployeeList.size();
        }

        @Override
        public Employee next() {
            Employee employee;

            try {
                employee = EmployeeList.get(curr_index);
            } catch (IndexOutOfBoundsException e){
                throw new NoSuchElementException();
            }
            curr_index ++;
            return employee;
        }


        /**
         * This method will add a new employee from the read input into the EmployeeList.
         *
         * @param employee the information of the Employee.
         */
        public void add(Employee employee){
            EmployeeList.add(employee);
        }
    }
    // ===============================
}