package com.ZYT.library.bin;

public class Main {

    public static int MENU = 1;


    public static void main(String[] args) {
        Menu menu = new Menu();
        for (; ; ) {
            if (MENU == 1) {
                menu.chooseLogin();
            } else if (MENU == 2) {
                menu.adminLogin();
            } else if (MENU == 3) {
                menu.adminMainMenu();
            } else if(MENU==4){
                menu.userLogin();
            } else if(MENU==5){
                menu.userMainMenu();
            }

        }
    }
}
