package Practica1;

import java.util.Objects;
import java.util.Scanner;
import java.io.FileReader;

class Practica1 {

    static String user = "sensei_201907179";
    static String password = "ipc1_201907179";
    static boolean logged = false;
    static int menuOption = 0;
    static String[][] ProductsMatrix = new String[15][2];
    static Scanner loginSc = new Scanner(System.in);
    static Scanner menuSc = new Scanner(System.in);


    public static void main(String[] args) {
        login();

    }

    public static void login() {
        do {
            String inUser, inPass;

            System.out.println("LOGIN");
            System.out.println("INGRESE SU USUARIO");
            inUser = loginSc.nextLine();
            System.out.println("INGRESE SU CONTRASEÑA");
            inPass = loginSc.nextLine();
            if (Objects.equals(inUser, user) && Objects.equals(inPass, password)) {
                System.out.println("BIENVENIDO ADMIN");
                logged = true;
            } else {
                System.out.println("DATOS INVALIDOS");
            }
        } while (!logged);
            menu();
    }

public static void menu(){
do {
    System.out.println("\n");
    System.out.println("*********************************************");
    System.out.println("*                                           *");
    System.out.println("*            MENÚ PRINCIPAL                 *");
    System.out.println("*                                           *");
    System.out.println("*********************************************");
    System.out.println("*  1. Agregar un producto                   *");
    System.out.println("*  2. Cargar productos (Masivo)             *");
    System.out.println("*  3. Realizar una venta                    *");
    System.out.println("*  4. Reporte Top 5 Productos más vendidos  *");
    System.out.println("*  5. Reporte Histórico de Ventas           *");
    System.out.println("*  6. Salir                                 *");
    System.out.println("*********************************************");
    System.out.print("Seleccione una opción: ");
    menuOption=menuSc.nextInt();
    
    
} while (logged);

}


}