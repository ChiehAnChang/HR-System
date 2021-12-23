package main.UsesCases;

import main.Entity.*;

import java.io.Serializable;
import java.util.Objects;


public class Verifier implements IVerifier, Serializable {


    /**
     * Verify if the user existed.
     *
     * @param userID The User ID of the user.
     *
     * @return true iff the User existed.
     */
    public boolean userExists(String userID, ILoginList loginList) {
        return !(Objects.isNull(loginList.getUser(userID)));
    }


    /**
     * Verify if the authority level of an employee is lower than the specific authority level.
     *
     * @param level The specific authority level that the client want to check.
     * @param userID the ID of the User.
     *
     * @return boolean represent if the specific authority level is higher than the employee's authority level.
     */
    @Override
    public boolean ValidToCreateThisLevel(String level, IEmployeeList employeeList, String userID) {
        if (level.length() != 1) {
            return false;
        }
        int LevelWantToCreate = Integer.parseInt(level);
        Employee employee = employeeList.getEmployee(userID);
        return LevelWantToCreate > employee.getLevel();
    }


    /**
     * Verify if the employee can be deleted.
     *
     * @param targetUserID The ID of the User the client want to delete.
     * @param userID the ID of the User.
     *
     * @return true iff the specific authority level is higher than the employee's authority level, which means that
     * the client has the authority to delete the user.
     */
    @Override
    public boolean validToDeleteThisUser(String targetUserID, IEmployeeList employeeList, String userID){
        Employee targetEmployee = employeeList.getEmployee(targetUserID);
        if (targetEmployee == null){
            return false;
        }else {
            Employee employee = employeeList.getEmployee(userID);
            return targetEmployee.getLevel() > employee.getLevel();
        }
    }


    /**
     * Check if the login is valid (if the username and password are correct).
     * @param userID the ID (username) of the account
     * @param password the password of the account
     * @return true iff the username and password of the user matched
     */
    @Override
    public boolean verifyForLogin(String userID, String password, ILoginList loginList) {
        for(Userable user: (LoginList) loginList){
            if(user.getID().equals(userID) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }


    /**
     * Verify the employee is full-time or not.
     *
     * @param userID the ID of employee.
     * @param employeeList the list of employees.
     *
     * @return true iff the employee is a full-time employee.
     */
    @Override
    public boolean verifierFullTime(String userID, IEmployeeList employeeList) {
        Employee e = employeeList.getEmployee(userID);
        return e instanceof FullTimeEmployee;
    }

    /**
     * Verify the leader of the Work.
     *
     * @param userID the ID of the Employee.
     * @param workID the ID of the Work.
     *
     * @return true iff the Employee is the Work's leader.
     */
    public boolean verifierLeader(String userID, String workID, IGroupList groupList) {
        for (Group g: (GroupList) groupList) {
            if (g.getWorkID().equals(workID)){return g.getLeaderID().equals(userID);}
        }
        return false;
    }


    /**
     * Verify the level of the User.
     *
     * @param userID the ID of the Employee.
     * @param level the authority level of the client.
     *
     * @return true iff the targeted Employee has a lower authority level than the client.
     */
    public boolean levelVerifier(int level, String userID, IEmployeeList employeeList) {
        return level > employeeList.getEmployee(userID).getLevel();
    }
}