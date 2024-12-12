package Practica1;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

class Practica1 {

    static String user = "ss";
    static String password = "dd";
    static String inUser, inPass;
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
    private static String[] detallesVentas = new String[MAX_VENTAS];

    public static void main(String[] args) {
        login();

    }

    public static void login() {
        System.out.println("\n*********************************************");
        System.out.println("*                 LOGIN                     *");
        System.out.println("*********************************************");

        do {

            System.out.println("\nIngrese sus credenciales:");
            System.out.print("Usuario: ");
            inUser = loginSc.nextLine();
            System.out.print("Contraseña: ");
            inPass = loginSc.nextLine();

            if (Objects.equals(inUser, user) && Objects.equals(inPass, password)) {
                System.out.println("\n*********************************************");
                System.out.println("*        ¡BIENVENIDO ADMINISTRADOR!         *");
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
            System.out.println("*            MENÚ PRINCIPAL                 *");
            System.out.println("*********************************************");
            System.out.println("  1. Agregar un producto                   ");
            System.out.println("  2. Cargar productos (Masivo)             ");
            System.out.println("  3. Ver Inventario                        ");
            System.out.println("  4. Realizar una venta                    ");
            System.out.println("  5. Reporte Top 5 Productos más vendidos  ");
            System.out.println("  6. Reporte Histórico de Ventas           ");
            System.out.println("  7. Salir                                 ");
            System.out.print("Seleccione una opción: ");

            try {
                menuOption = menuSc.nextInt(); // Capturamos la opción seleccionada
                menuSc.nextLine(); // Limpiar el buffer

                // Verificamos si la opción está dentro del rango permitido
                if (menuOption < 1 || menuOption > 7) {
                    System.out.println("\n");
                    System.out.println("       HA INGRESADO UNA OPCIÓN INVÁLIDA.   ");
                    System.out.println("\nPor favor, seleccione una opción válida.");
                    continue; // Volver a mostrar el menú
                }

                // Ejecutar la acción según la opción seleccionada
                switch (menuOption) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        cargaMasiva();
                        break;
                    case 3:
                        verInventario();
                        break;
                    case 4:
                        realizarVenta();
                        break;
                    case 5:
                        reporteTop();
                        break;
                    case 6:
                        reporteHistorico();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        System.out.println("\n¡VUELVA PRONTO, ADMINISTRADOR!");
                        System.out.println("\nGracias por usar el sistema.");
                        System.out.println("Cerrando la aplicación...");
                        System.exit(0); // Termina el programa

                        break;
                    default:
                        // Este caso nunca ocurrirá debido a la validación previa
                        System.out.println("\nOpción inválida. Intente de nuevo.");
                }
            } catch (Exception e) {
                // Si ocurre una excepción (por ejemplo, ingreso de letras o caracteres no
                // válidos)
                System.out.println("\n");
                System.out.println("       HA INGRESADO UNA OPCIÓN INVÁLIDA.   ");
                System.out.println("\nPor favor, seleccione una opción válida.");
                menuSc.nextLine(); // Limpiar el buffer para evitar un bucle infinito
            }
        } while (menuOption != 7); // Repite si la opción no es salir
    }

    public static void agregarProducto() {

        try {
            if (Product_Counter >= Max_Productos) {
                System.out.println("          INVENTARIO LLENO.               ");
                System.out.println("\nNo se pueden agregar más productos.");
                return;
            }

            System.out.print("\nIngrese los datos del producto:\n");

            System.out.print("Ingrese el nombre del producto: ");
            String nombre = agregarProductoSc.nextLine();

            if (existeProducto(nombre)) {
                System.out.println("\nEl producto ya existe en el inventario.");
                return;
            }

            Double precio = null;
            boolean precioValido = false;

            while (!precioValido) {
                try {
                    System.out.print("Ingrese el precio del producto: ");
                    precio = agregarProductoSc.nextDouble();
                    agregarProductoSc.nextLine(); // Limpiar el buffer
                    if (precio <= 0) {
                        System.out.println("\nEl precio debe ser mayor a 0. Intente de nuevo.");
                    } else {
                        precioValido = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nEntrada inválida. Por favor, ingrese un número válido para el precio.");
                    agregarProductoSc.nextLine(); // Limpiar el buffer para evitar errores posteriores
                }
            }

            agregarProductoInventario(nombre, precio);
        } catch (Exception e) {
            System.out.println("\nOcurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void cargaMasiva() {
        // Crear un JFrame como componente padre para mostrar el diálogo
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo CSV para cargar los productos");

        int result = fileChooser.showOpenDialog(frame); // Usar frame como componente padre

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String Line;
                boolean isFirstLine = true;

                while ((Line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // Saltar el encabezado
                        continue;
                    }

                    String[] parts = Line.split(";");
                    if (parts.length == 2) {
                        String nombre = parts[0];
                        try {
                            double precio = Double.parseDouble(parts[1]);

                            // Validación de precio
                            if (precio <= 0) {
                                System.out.println("El precio debe ser mayor a 0 en la línea: " + Line);
                                continue;
                            }

                            // Validación de duplicado
                            if (existeProducto(nombre)) {
                                System.out.println("\nEl producto ya existe en el inventario.");
                                continue;
                            }

                            agregarProductoInventario(nombre, precio);

                        } catch (NumberFormatException e) {
                            System.out.println("Formato de precio inválido en la línea: " + Line);
                        }
                    }
                }

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }

        frame.dispose(); // Cerrar el JFrame después de usarlo
    }

    public static void agregarProductoInventario(String nombre, double precio) {
        if (Product_Counter >= Max_Productos) {
            System.out.println("No se pueden agregar más productos.");
            return;
        }

        ProductsMatrix[Product_Counter][0] = nombre;
        ProductsMatrix[Product_Counter][1] = Double.toString(precio);
        ProductsMatrix[Product_Counter][2] = "0";
        Product_Counter++;
        System.out.println("Producto agregado correctamente.");
    }

    public static boolean existeProducto(String nombre) {
        for (int i = 0; i < Product_Counter; i++) {
            if (ProductsMatrix[i][0].equalsIgnoreCase(nombre)) {
                return true; // El producto ya existe
            }
        }
        return false; // El producto no existe
    }

    public static void verInventario() {
        System.out.println("\nProductos en inventario:");

        for (int i = 0; i < Product_Counter; i++) {
            System.out.println(
                    (i + 1) + ". " + ProductsMatrix[i][0] + " - Precio: Q" + ProductsMatrix[i][1] + " ----- Vendidos: "
                            + ProductsMatrix[i][2]);
        }
    }

    public static void realizarVenta() {
        // Verificar si el inventario tiene productos disponibles
        if (Product_Counter == 0) {
            System.out.println("No hay productos disponibles en inventario. No se puede realizar la venta.");
            return; // Salir del método si no hay productos disponibles
        }

        // Solicita información del cliente
        System.out.print("\nNombre del cliente: ");
        String clienteNombre = ventaScanner.nextLine();
        System.out.print("NIT del cliente (o 'C/F' si no lo tiene): ");
        String clienteNIT = ventaScanner.nextLine();

        // Variable para acumular el total de la venta
        double totalVentaActual = 0;
        StringBuilder detallesCompra = new StringBuilder();

        boolean realizarCompras = true;
        do {
            boolean seleccionValida = false;

            do {
                // Mostrar productos disponibles
                System.out.println("\nProductos disponibles:");
                verInventario();

                // Solicita la selección de producto
                System.out.print("Seleccione el producto para comprar (número o 0 para salir): ");
                int seleccion;
                try {
                    seleccion = Integer.parseInt(ventaScanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Opción inválida. Intente nuevamente.");
                    continue;
                }

                if (seleccion == 0) {
                    realizarCompras = false; // El usuario decidió salir
                    break;
                }

                // Validar selección del producto
                if (seleccion >= 1 && seleccion <= Product_Counter) {
                    int cantidad;
                    do {
                        // Solicita la cantidad de producto
                        System.out.print("Ingrese cantidad (debe ser mayor o igual a 1): ");
                        try {
                            cantidad = Integer.parseInt(ventaScanner.nextLine().trim());
                            if (cantidad < 1) {
                                System.out.println("Cantidad inválida. Intente de nuevo.");
                            }
                        } catch (NumberFormatException e) {
                            cantidad = 0; // Reiniciar en caso de error
                            System.out.println("Cantidad inválida. Intente de nuevo.");
                        }
                    } while (cantidad < 1);

                    // Procesar compra
                    double precioUnitario = Double.parseDouble(ProductsMatrix[seleccion - 1][1]);
                    double totalProducto = cantidad * precioUnitario;

                    // Actualizar el total de la venta
                    totalVentaActual += totalProducto;

                    // Actualizar el inventario dinámico
                    ProductsMatrix[seleccion - 1][2] = String.valueOf(
                            Integer.parseInt(ProductsMatrix[seleccion - 1][2]) + cantidad);

                    // Registrar detalles de compra para la factura con subtotales
                    detallesCompra.append(ProductsMatrix[seleccion - 1][0])
                            .append(" - Q")
                            .append(precioUnitario)
                            .append(" x ")
                            .append(cantidad)
                            .append(" = Q")
                            .append(totalProducto)
                            .append("\n");

                    System.out.println("Producto agregado a la factura.");
                    // Imprimir el detalle del subtotal
                    System.out.println(
                            ProductsMatrix[seleccion - 1][0] + " x Q" + precioUnitario + " = Q" + totalProducto);
                    System.out.println("---------------------------------");
                    System.out.println("Subtotal: Q" + totalVentaActual);
                    seleccionValida = true; // Salir del bucle interno al procesar una selección válida
                } else {
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            } while (!seleccionValida);

        } while (realizarCompras);

        // Registrar la venta en los arrays correspondientes
        registrarVenta(clienteNombre, clienteNIT, totalVentaActual, detallesCompra.toString());

        // Generar la factura después de finalizar la selección
        generarFactura(clienteNombre, clienteNIT, totalVentaActual, detallesCompra.toString());
    }

    private static void registrarVenta(String cliente, String nit, double total, String detalle) {
        if (contadorVentas < MAX_VENTAS) {
            clientes[contadorVentas] = cliente;
            nits[contadorVentas] = nit;
            totalesVentas[contadorVentas] = total;
            detallesVentas[contadorVentas] = detalle;
            contadorVentas++;
            System.out.printf("  Venta registrada exitosamente. Total: Q%.2f%n", total);
        } else {
            System.out.println("  No se pueden registrar más ventas. Límite alcanzado.");
        }
    }

    public static void generarFactura(String clienteNombre, String clienteNIT, double total, String detallesCompra) {
        String nombreArchivo = clienteNombre.replaceAll("[^a-zA-Z0-9\\s]", "").replace(" ", "_") + "_" + clienteNIT
                + ".html";
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(nombreArchivo), "UTF-8")) {
            // Crear contenido HTML de la factura
            writer.write("<html><head><title>Factura</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }");
            writer.write("h1 { background-color: #6200ea; color: white; padding: 16px; margin: 0; }");
            writer.write(".container { padding: 20px; max-width: 600px; margin: auto; background-color: white; border: 1px solid #ddd; }");
            writer.write("table { width: 100%; border-collapse: collapse; margin-top: 20px; background-color: white; }");
            writer.write("th, td { text-align: left; padding: 12px; border-bottom: 1px solid #ddd; }");
            writer.write("th { background-color: #6200ea; color: white; }");
            writer.write("tr:hover { background-color: #f1f1f1; }");
            writer.write("</style></head><body>");

            writer.write("<h1>Factura de Compra</h1>");
            writer.write("<div class='contenedor'>");
            writer.write("<p><b>Cliente:</b> " + clienteNombre + "</p>");
            writer.write("<p><b>NIT:</b> " + clienteNIT + "</p>");
            writer.write("<table><tr><th>Detalle</th><th>Cantidad</th><th>Precio</th><th>Total</th></tr>");

            // Procesar los detalles de la compra
            String[] lineasDetalles = detallesCompra.split("\n");
            for (String linea : lineasDetalles) {
                if (!linea.isEmpty()) {
                    try {
                        // Divide la línea en "Producto" y el resto después de " - Q"
                        String[] partes = linea.split(" - Q");
                        String producto = partes[0].trim();
                        String[] detalles = partes[1].split(" x ");
                        double precioUnitario = Double.parseDouble(detalles[0].trim());
                        String[] cantidadYSubtotal = detalles[1].split(" = Q");
                        int cantidad = Integer.parseInt(cantidadYSubtotal[0].trim());
                        double subtotal = Double.parseDouble(cantidadYSubtotal[1].trim());

                        // Escribir en la tabla
                        writer.write(
                                "<tr><td>" + producto + "</td><td>" + cantidad + "</td><td>Q" + precioUnitario
                                        + "</td><td>Q"
                                        + subtotal + "</td></tr>");
                    } catch (Exception e) {
                        System.out.println("Error procesando la línea de detalles: " + linea);
                    }
                }
            }

            writer.write("</table>");
            writer.write("<h3 style='text-align:center;'>Total: Q" + total + "</h3>");
            writer.write("</div></body></html>");

            System.out.println("Factura generada exitosamente: factura.html");
        } catch (IOException e) {
            System.out.println("Error al generar la factura: " + e.getMessage());
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

        // Ordenación simple de productos
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
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("reporte_top5.html"), "UTF-8")) {// FileWriter

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

}