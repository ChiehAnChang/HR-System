package main.Framework;

import main.InterfaceAdapter.FacadeSys;

import java.util.List;
import java.util.Scanner;

public class CheckSalaryUI {

    // === Instance Variables ===
    private final FacadeSys facadeSys;


    /**
     * Construct a CheckSalaryUI
     * @param facadeSys A FacadeSys type object that is going to be used in the UI
     */
    public CheckSalaryUI(FacadeSys facadeSys) {
        this.facadeSys = facadeSys;
    }


    /**
     * Run the CheckSalaryUI
     */
    public void run(){
        Scanner keyIn = new Scanner(System.in);
        boolean noExist = true;
        System.out.println("You can check the salary-related information about all " +
                "the employees whose level is lower than you");
        while (noExist) {
            System.out.println(
                    "i) Check lower level employees' salary, please type 1; " + "\n" +
                    "ii) Check lower level employees' total vacation with salary, please type 2; " + "\n" +
                    "iii) Check lower level employees' vacation used, please type 3; " + "\n" +
                    "iv) Otherwise, please type E to exit;");
            String check_option = keyIn.nextLine().toUpperCase();
            if (check_option.equals("E")) {
               break;
            }else {
                try {
                    List<String> result = this.facadeSys.checkLowerLevelEmployeeSalary();

                    if (check_option.equals("1") || check_option.equals("2") || check_option.equals("3")) {
                        System.out.println(result.get(Integer.parseInt(check_option) - 1));
                        System.out.println();
                        System.out.println("If you want to check other type of the information from the lower level employees," +
                                " please type any bottom. Otherwise, please type E to exist");
                    } else {
                        System.out.println("Please give the correct action! Try again.");
                    }
                    System.out.println();
                    System.out.println("Otherwise, please type E to exist");
                    String action = keyIn.nextLine().toUpperCase();
                    if (action.equalsIgnoreCase("E")) {
                        System.out.println();
                        noExist = false;
                    }
                }catch (Exception e){
                    System.out.println("There are not lower level user exist! \n");
                }

            }
        }
    }
}
