package main.UsesCases;

import main.Entity.*;

import java.io.Serializable;
import java.util.ArrayList;


public class PersonalManager implements IPersonalManager , Serializable {

    // === Instance Variables ===
    private final SalaryCalculator salaryCalculator;

    /**
     * Construct the PersonalManager, managing the information from other UsesCases.
     */
    public PersonalManager() {
        KPICalculator kpiCalculator = new KPICalculator();
        this.salaryCalculator = new SalaryCalculator(kpiCalculator);
    }

    //====== Methods regarding retrieve personal information =======
    /**
     * Get the basic information of an employee such as name, ID, username, password, phone number, address,
     * and department.
     *
     * @return The ArrayList that contains the basic information of an employee.
     */
    @Override
    public ArrayList<String> employeeInfo(ILoginList loginList, IEmployeeList employeeList, String userID) {
        ArrayList<String> info = new ArrayList<>();
        Userable user = loginList.getUser(userID);
        Employee employee = employeeList.getEmployee(userID);
        info.add(user.getName());
        info.add(user.getID());
        info.add(user.getPassword());
        info.add(user.getPhone());
        info.add(user.getAddress());
        if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
            info.add(partTimeEmployee.getDepartment());
        } else {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            info.add(fullTimeEmployee.getDepartment());
            info.add(fullTimeEmployee.getPosition());
            info.add(fullTimeEmployee.getState());
            info.add(fullTimeEmployee.getTotalVacationWithSalary());
            info.add(fullTimeEmployee.getVacationUsed());
        }
        return info;
    }



    /**
     * Getter method for the schedule of a part-time employee.
     *
     * @return A HashMap that contains the schedule of a part-time employee.
     */
    @Override
    public String getWorkingHourFromPartTimeEmployee(IEmployeeList employeeList, String userID) {
        PartTimeEmployee employee = (PartTimeEmployee) employeeList.getEmployee(userID);
        return employee.getWorkingHour();
    }

    //==== retrieve employee's salary information ===
    /**
     * Check the total salary of an employee (including bonus).
     *
     * @return An int that represent the salary/wage of an employee.
     */
    @Override
    public String checkTotalSalary(IEmployeeList employeeList, String userID, IGroupList groupList, IWorkList workList) {
        Employee employee = employeeList.getEmployee(userID);
        if (employee instanceof PartTimeEmployee) {
            return this.salaryCalculator.calculatePartTimeSalary((PartTimeEmployee) employee);
        } else {
            return this.salaryCalculator.calculateFullTimeSalary((FullTimeEmployee) employee, groupList, workList);
        }
    }


    /**
     * Check the minimum wage of an employee.
     *
     * @param userID the ID of the targeted user.
     *
     * @return A string that represent the minimum wage of an employee.
     */
    @Override
    public String checkMinimumWage(IEmployeeList employeeList, String userID) {
        Employee employee = employeeList.getEmployee(userID);
        return employee.getWage();
    }


    /**
     * Check the vocation bonus of an employee.
     *
     * @param userID the ID of the targeted user.
     *
     * @return A string that represent the vocation bonus of an employee.
     */
    @Override
    public String checkVacationBonus(IEmployeeList employeeList, String userID) {
        Employee employee = employeeList.getEmployee(userID);
        if (employee instanceof PartTimeEmployee) {
            return "0";
        } else {
            return this.salaryCalculator.calculateBonusFromVacation((FullTimeEmployee) employee);
        }
    }


    /**
     * Check the KPI bonus of an employee.
     *
     * @param userID the ID of the targeted user.
     *
     * @return A string that represent the KPI bonus of an employee.
     */
    @Override
    public String checkKPIBonus(IEmployeeList employeeList, String userID, IGroupList groupList, IWorkList workList){
        Employee employee = employeeList.getEmployee(userID);
        if (employee instanceof PartTimeEmployee) {
            return "0";
        } else {
            return this.salaryCalculator.calculateBonusFromKPI((FullTimeEmployee) employee,groupList,workList);
        }
    }

    // ==== retrieve full time employee's vacation information =====
    @Override
    public ArrayList<String> vacationInfo(IEmployeeList employeeList, String userID) {
        ArrayList<String> vacationList = new ArrayList<>();
        Employee employee = employeeList.getEmployee(userID);
        if (employee instanceof PartTimeEmployee) {
            return vacationList;
        }
        vacationList.add(String.valueOf(((FullTimeEmployee)employee).getVacationUsed()));
        vacationList.add(String.valueOf(((FullTimeEmployee)employee).getTotalVacationWithSalary()));
        return vacationList;
    }

    // ==== retrieve user's level ====
    @Override
    public String getUserLevel(String userID, IEmployeeList employeeList) {
        return String.valueOf(employeeList.getEmployee(userID).getLevel());
    }


    // ==== setter methods for changing user/employee's information ====
    @Override
    public void setName(String response, ILoginList loginList, String userID){
        loginList.getUser(userID).setName(response);
    }

    @Override
    public void setPassword(String response, ILoginList loginList, String userID){
        loginList.getUser(userID).setPassword(response);
    }

    @Override
    public void setPhone(String response, ILoginList loginList, String userID){
        loginList.getUser(userID).setPhone(response);
    }

    @Override
    public void setAddress(String response, ILoginList loginList, String userID){
        loginList.getUser(userID).setAddress(response);
    }

    @Override
    public void setDepartment(String userID, String department, IEmployeeList employeeList){
        Employee employee = employeeList.getEmployee(userID);
        employee.setDepartment(department);
    }

    @Override
    public void setLevel(String userID, String level, IEmployeeList employeeList){
        Employee employee = employeeList.getEmployee(userID);
        employee.setLevel(Integer.parseInt(level));
    }

    @Override
    public void setWage(String userID, String wage, IEmployeeList employeeList){
        Employee employee = employeeList.getEmployee(userID);
        employee.setWage(wage);
    }

    @Override
    public boolean setTotalVacationWithSalary(String userID, String vacation, IEmployeeList employeeList){
        try{
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employeeList.getEmployee(userID);
            fullTimeEmployee.setTotalVacationWithSalary(vacation);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    @Override
    public boolean setVacationUsed(String userID, String vacationUsed, IEmployeeList employeeList){
        try{
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employeeList.getEmployee(userID);
            fullTimeEmployee.setVacationUsed(vacationUsed);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean setWorkingHour(String userID, String hours, IEmployeeList employeeList) {
        try{
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employeeList.getEmployee(userID);
            partTimeEmployee.addWorkingHour(hours);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    @Override
    public boolean resetWorkingHour(String userID, IEmployeeList employeeList){
        try{
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employeeList.getEmployee(userID);
            partTimeEmployee.resetWorkingHour();
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public boolean setPosition(String userID, String position, IEmployeeList employeeList){
        try{
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employeeList.getEmployee(userID);
            fullTimeEmployee.setPosition(position);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean setEmployeeState(String userID, String state, IEmployeeList employeeList){
        try{
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employeeList.getEmployee(userID);
            fullTimeEmployee.setState(state);
            return true;
        } catch (Exception e){
            return false;
        }
    }




}
