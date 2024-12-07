package Practica1;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JFileChooser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Practica1 {

    static String user = "ss";// sensei_201907179
    static String password = "dd";// ipc1_201907179
    static boolean logged = false;
    static int menuOption = 0;
    static int Max_Productos = 15;
    static String[][] ProductsMatrix = new String[Max_Productos][3];
    static Scanner loginSc = new Scanner(System.in);
    static Scanner menuSc = new Scanner(System.in);
    static Scanner agregarProductoSc = new Scanner(System.in);
    static Scanner ventaScanner = new Scanner(System.in);
    static int Product_Counter = 0;
    static String cliente;
    static String nit;
    static int totalProductos = 0;
    static double totalVenta = 0.0;
    static int index = 0;
    static double precioVenta = 0.0;
    private static int contadorVentas = 0;
    private static final int MAX_VENTAS = 100;
    private static String[] clientes = new String[MAX_VENTAS];
    private static String[] nits = new String[MAX_VENTAS];
    private static double[] totalesVentas = new double[MAX_VENTAS];
    private static String[] detallesVentas = new String[MAX_VENTAS]

    public static void main(String[] args) {
        login();

    }

    public static void login() {
        System.out.println("\n*********************************************");
        System.out.println("*                 LOGIN                     *");
        System.out.println("*********************************************");

        do {
            String inUser, inPass;

            System.out.println("\nIngrese sus credenciales:");
            System.out.print("Usuario: ");
            inUser = loginSc.nextLine();
            System.out.print("Contraseña: ");
            inPass = loginSc.nextLine();

            if (Objects.equals(inUser, user) && Objects.equals(inPass, password)) {
                System.out.println("\n*********************************************");
                System.out.println("*        ¡BIENVENIDO ADMINISTRADOR!        *");
                System.out.println("*********************************************");
                logged = true;
            } else {
                System.out.println("\n---------------------------------------------");
                System.out.println("       Usuario o contraseña incorrectos.    ");
                System.out.println("        Por favor, inténtelo de nuevo.       ");
                System.out.println("---------------------------------------------");
            }
        } while (!logged);

        menu(); // Llama al menú después de iniciar sesión correctamente
    }

    public static void menu() {
        do {
            System.out.println("\n");
            System.out.println("*********************************************");
            System.out.println("*                                           *");
            System.out.println("*            MENÚ PRINCIPAL                 *");
            System.out.println("*                                           *");
            System.out.println("*********************************************");
            System.out.println("*  1. Agregar un producto                   *");
            System.out.println("*  2. Cargar productos (Masivo)             *");
            System.out.println("*  3. Ver Inventario                        *");
            System.out.println("*  4. Realizar una venta                    *");
            System.out.println("*  5. Reporte Top 5 Productos más vendidos  *");
            System.out.println("*  6. Reporte Histórico de Ventas           *");
            System.out.println("*  7. Salir                                 *");
            System.out.println("*********************************************");
            System.out.print("Seleccione una opción: ");

            // Intentamos leer la opción con manejo de excepciones
            try {
                menuOption = menuSc.nextInt(); // Capturamos la opción seleccionada

                // Verificamos si la opción está dentro del rango permitido
                if (menuOption < 1 || menuOption > 7) {
                    System.out.println("\n*********************************************");
                    System.out.println("*                                           *");
                    System.out.println("*       HA INGRESADO UNA OPCIÓN INVÁLIDA.   *");
                    System.out.println("*                                           *");
                    System.out.println("*********************************************");
                    System.out.println("\nPor favor, seleccione una opción válida.");
                }
            } catch (Exception e) {
                // Si ocurre una excepción (por ejemplo, ingreso de letras o caracteres no
                // válidos)
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*       INGRESO INVALIDO.                   *");
                System.out.println("*  Por favor, ingrese un número válido.     *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");
                menuSc.nextLine(); // Limpiar el buffer para evitar un bucle infinito
            }

        } while (menuOption < 1 || menuOption > 7); // Repite si la opción es inválida

        // Llamada al caso correspondiente según la opción seleccionada
        switch (menuOption) {
            case 1:
                agregarProducto();
                menu();
                break;
            case 2:
                cargaMasiva();
                menu();
                break;
            case 3:
                verInventario();
                menu();
                break;
            case 4:
                realizarVenta();
                menu();
                break;
            case 5:
                reporteTop();
                menu();
                break;
            case 6:
                reporteHistorico();
                break;
            case 7:
                salir();
                break;

            default:
                // Este caso ya no será alcanzado, ya que validamos la entrada previamente
                break;
        }
    }

    public static void agregarProducto() {
        if (Product_Counter >= Max_Productos) {
            System.out.println("\n*********************************************");
            System.out.println("*                                           *");
            System.out.println("*          INVENTARIO LLENO.               *");
            System.out.println("*                                           *");
            System.out.println("*********************************************");
            System.out.println("\nNo se pueden agregar más productos. Por favor, elimine algunos para continuar.");
            return;
        } else {

            System.out.print("\n*********************************************\n");
            System.out.print("*                                           *\n");
            System.out.print("*        INGRESE LOS DATOS DEL PRODUCTO     *\n");
            System.out.print("*                                           *\n");
            System.out.print("*********************************************\n");

            System.out.print("Ingrese el nombre del producto: ");
            String nombre = agregarProductoSc.nextLine();

            System.out.print("Ingrese el precio del producto: ");
            Double precio = agregarProductoSc.nextDouble();
            agregarProductoSc.nextLine();

            // Validación del precioFFF
            if (precio <= 0) {
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*     El precio debe ser mayor a 0.          *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");
                return;
            } else {
                ProductsMatrix[Product_Counter][0] = nombre;
                ProductsMatrix[Product_Counter][1] = Double.toString(precio);
                ProductsMatrix[Product_Counter][2] = "0"; // contador de vendidos

                Product_Counter++;
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*      Producto agregado correctamente.     *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");

            }
        }
    }

    public static void cargaMasiva() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo CSV para cargar los productos");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String Line;
                boolean isFirstLine = true;

                while ((Line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // saltamos el encabezado
                        continue;
                    }

                    String[] parts = Line.split(",");
                    if (parts.length == 2) {
                        String nombre = parts[0];
                        try {
                            double precio = Double.parseDouble(parts[1]);
                            // Validación del precioFFF
                            if (precio <= 0) {
                                System.out.println("\n*********************************************");
                                System.out.println("*                                           *");
                                System.out.println("*     El precio debe ser mayor a 0.          *");
                                System.out.println("*                                           *");
                                System.out.println("*********************************************");
                                return;
                            } else {
                                ProductsMatrix[Product_Counter][0] = nombre;
                                ProductsMatrix[Product_Counter][1] = Double.toString(precio);
                                ProductsMatrix[Product_Counter][2] = "0"; // contador de vendidos

                                Product_Counter++;
                                System.out.println("\n*********************************************");
                                System.out.println("*                                           *");
                                System.out.println("*      Producto agregado correctamente.     *");
                                System.out.println("*                                           *");
                                System.out.println("*********************************************");

                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Formato de precio inválido en la línea: " + Line); // TODO: handle
                                                                                                   // exception
                        }

                    }
                    System.out.println("Línea inválida: " + Line);

                }

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }

        } else {
            System.out.println("Operación cancelada.");
        }
    }

    public static void verInventario() {
        System.out.println("\nProductos en inventario:");
        for (int i = 0; i < Product_Counter; i++) {
            System.out.println(i + ". " + ProductsMatrix[i][0] + " ----- Q" + ProductsMatrix[i][1] + " ----- Vendidos: "
                    + ProductsMatrix[i][2]);
        }

    }

    public static void realizarVenta() {
        System.out.println("\n*********************************************");
        System.out.println("*                                           *");
        System.out.println("*      INGRESO DE DATOS DEL CLIENTE         *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");

        System.out.print("Ingrese el nombre del cliente: ");
        cliente = ventaScanner.nextLine();

        System.out.print("Ingrese el NIT (o C/F si no aplica): ");
        nit = ventaScanner.nextLine();

        while (true) {
            verInventario();
            System.out.println("\n*********************************************");

            System.out.print("Ingrese el índice del producto a comprar (o -1 para finalizar): ");

            index = -1;
            try {
                index = ventaScanner.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*     ❌ Ingrese un número válido para el índice. *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");
                ventaScanner.nextLine(); // Limpiar buffer
                continue;
            }
            if (index == -1)
                break; // Finaliza la venta si el usuario ingresa -1

            if (index >= 0 && index < Product_Counter) {
                System.out.print("Ingrese la Cantidad que desea ");

                try {
                    totalProductos = ventaScanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\n*********************************************");
                    System.out.println("*                                           *");
                    System.out.println("*       Ingrese una cantidad válida.        *");
                    System.out.println("*                                           *");
                    System.out.println("*********************************************");
                    ventaScanner.nextLine(); // Limpiar buffer
                    continue;
                }
                if (totalProductos <= 0) {
                    System.out.println("\n*********************************************");
                    System.out.println("*                                           *");
                    System.out.println("*     ❌ La cantidad debe ser mayor que 0.    *");
                    System.out.println("*                                           *");
                    System.out.println("*********************************************");
                    continue;
                }

                precioVenta = Double.parseDouble(ProductsMatrix[index][1]);

                totalVenta = precioVenta * totalProductos;
                cantidadesVendidas[index - 1] += totalProductos;
                detalle.append(nombresProductos[index - 1])
                       .append(" x")
                       .append(totalProductos)
                       .append(" - Q")
                       .append(pre[index - 1] * totalProductos)
                       .append("\n");
                clientes[contadorVentas] = cliente;
                nits[contadorVentas] = nit;
                totalesVentas[contadorVentas] = totalVenta;

                detallesVentas[contadorVentas] = detalle.toString();
                contadorVentas++;

                // Mostrar detalles del producto
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*   Producto: " + ProductsMatrix[index][0] + " | Cantidad: " + totalProductos
                        + " | Total: Q" + (precioVenta * totalProductos) + " *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");

            } else {
                System.out.println("\n*********************************************");
                System.out.println("*                                           *");
                System.out.println("*     ❌ Índice inválido, intente de nuevo.   *");
                System.out.println("*                                           *");
                System.out.println("*********************************************");
            }

        }

    }

    public static void reporteTop() {
        if (Product_Counter == 0) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        // Ordenar productos por cantidad vendida (descendente)
        String[] topProductos = new String[Product_Counter];
        double[] topPrecios = new double[Product_Counter];
        int[] topCantidades = new int[Product_Counter];

        for (int i = 0; i < Product_Counter; i++) {
            topProductos[i] = ProductsMatrix[i][0];
            topPrecios[i] = Double.parseDouble(ProductsMatrix[i][1]);
            topCantidades[i] = Integer.parseInt(ProductsMatrix[i][2]);
        }

        // Ordenación simple de productos (puedes usar otros métodos como "Arrays.sort"
        // si prefieres)
        for (int i = 0; i < Product_Counter - 1; i++) {
            for (int j = i + 1; j < Product_Counter; j++) {
                if (topCantidades[i] < topCantidades[j]) {
                    // Intercambiar productos, precios y cantidades
                    String tempProducto = topProductos[i];
                    double tempPrecio = topPrecios[i];
                    int tempCantidad = topCantidades[i];

                    topProductos[i] = topProductos[j];
                    topPrecios[i] = topPrecios[j];
                    topCantidades[i] = topCantidades[j];

                    topProductos[j] = tempProducto;
                    topPrecios[j] = tempPrecio;
                    topCantidades[j] = tempCantidad;
                }
            }
        }

        // Crear archivo HTML para el reporte
        try (FileWriter writer = new FileWriter("reporte_top5.html")) {
            writer.write("<html><head><title>Reporte Top 5 Productos</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }");
            writer.write("h1 { background-color: #6200ea; color: white; padding: 16px; margin: 0; }");
            writer.write(".container { padding: 20px; }");
            writer.write(
                    "table { width: 100%; border-collapse: collapse; margin-top: 20px; background-color: white; }");
            writer.write("th, td { text-align: left; padding: 12px; border-bottom: 1px solid #ddd; }");
            writer.write("th { background-color: #6200ea; color: white; }");
            writer.write("tr:hover { background-color: #f1f1f1; }");
            writer.write("</style></head><body>");
            writer.write("<h1>Top 5 Productos Más Vendidos</h1>");
            writer.write("<div class='container'>");
            writer.write("<table><tr><th>Producto</th><th>Precio</th><th>Cantidad Vendida</th></tr>");

            for (int i = 0; i < Math.min(5, Product_Counter); i++) {
                writer.write("<tr><td>" + topProductos[i] + "</td><td>Q" + topPrecios[i] + "</td><td>"
                        + topCantidades[i] + "</td></tr>");
            }

            writer.write("</table></div></body></html>");
            System.out.println("Reporte Top 5 generado exitosamente: reporte_top5.html");
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    public static void reporteHistorico() {
        if (contadorVentas == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        try (FileWriter writer = new FileWriter("reporte_historico.html")) {
            writer.write("<html><head><title>Reporte Histórico de Ventas</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }");
            writer.write("h1 { background-color: #6200ea; color: white; padding: 16px; margin: 0; }");
            writer.write(".container { padding: 20px; }");
            writer.write(
                    "table { width: 100%; border-collapse: collapse; margin-top: 20px; background-color: white; }");
            writer.write("th, td { text-align: left; padding: 12px; border-bottom: 1px solid #ddd; }");
            writer.write("th { background-color: #6200ea; color: white; }");
            writer.write("tr:hover { background-color: #f1f1f1; }");
            writer.write("</style></head><body>");
            writer.write("<h1>Histórico de Ventas</h1>");
            writer.write("<div class='container'>");
            writer.write("<table><tr><th>Cliente</th><th>NIT</th><th>Total</th><th>Detalle</th></tr>");

            for (int i = 0; i < contadorVentas; i++) {
                writer.write(
                        "<tr><td>" + clientes[i] + "</td><td>" + nits[i] + "</td><td>Q" + totalesVentas[i] + "</td>");
                writer.write("<td>" + detallesVentas[i].replace("\n", "<br>") + "</td></tr>");
            }

            writer.write("</table></div></body></html>");
            System.out.println("Reporte histórico generado exitosamente: reporte_historico.html");
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    public static void salir() {
        System.out.println("\n*********************************************");
        System.out.println("*                                           *");
        System.out.println("*          ¡VUELVA PRONTO, ADMINISTRADOR!    *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
        System.out.println("\nGracias por usar el sistema.");
        System.out.println("Cerrando la aplicación...");
        System.exit(0); // Termina el programa

    }
}