package main.Framework;

import main.InterfaceAdapter.FacadeSys;

import java.util.Scanner;

public class DeleteUserUI {

    // === Instance Variables ===
    private final FacadeSys facadeSys;


    /**
     * Construct a DeleteUserUI
     * @param facadeSys A FacadeSys type object that is going to be used in the UI
     */
    public DeleteUserUI(FacadeSys facadeSys) {
        this.facadeSys = facadeSys;
    }


    /**
     * Run the DeleteUserUI
     */
    public void run() {
        Scanner keyIn = new Scanner(System.in);
        boolean noExist = true;
        while (noExist){
            System.out.println("Please enter the ID of the user you want to delete");
            String userId = keyIn.nextLine();

            boolean SuccessDeleteNewUser= this.facadeSys.deleteUser(userId);
            if (SuccessDeleteNewUser){
                System.out.println(
                        "If you want to delete another user, please type C. \n" +
                                " Otherwise, please type E to exist");
                String action = keyIn.nextLine().toUpperCase();
                if(action.equalsIgnoreCase("E")){
                    noExist = false;
                }
            }
            else {
                System.out.println("You cannot delete this level of the user or there are no user to delete! Please type again.");
                System.out.println();
                noExist = false;
            }
        }
    }
}
