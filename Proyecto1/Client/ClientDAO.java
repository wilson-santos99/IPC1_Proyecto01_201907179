package Proyecto1.Client;
import java.io.Serializable;
import java.util.ArrayList;
public class ClientDAO implements Serializable {
    public ArrayList<Client> clients; // Lista de clientes
    private static ClientDAO instance; // Instancia única para Singleton
    // Constructor privado para Singleton
    private ClientDAO() {
        this.clients = new ArrayList<>(); // Inicializar la lista de clientes
    }
    // Método para obtener la instancia única de ClientDAO
    public static ClientDAO getInstance() {
        if (instance == null) {
            instance = new ClientDAO();
        }
        return instance;
    }
    /**
     * Agregar un cliente a la lista
     * @param name Nombre del cliente.
     * @param nit  NIT del cliente. Si el NIT es "CF", se permite duplicidad.
     * @return true si se agrega correctamente, false si hay duplicidad en NIT diferente de "CF".
     */
    public boolean addClient(String name, String nit) {
        // Si el NIT no es "CF", verificar si ya existe un cliente con el mismo NIT
        if (!nit.equalsIgnoreCase("CF")) {
            for (Client c : clients) {
                if (c.getNit().equalsIgnoreCase(nit)) {
                    return false; // Cliente duplicado para NIT diferente de "CF"
                }
            }
        }
        // Agregar el cliente a la lista
        clients.add(new Client(name, nit));
        return true;
    }
    /**
     * Actualizar el NIT de un cliente existente.
     * @param name   Nombre del cliente cuyo NIT se va a actualizar.
     * @param newNit Nuevo NIT. Si el NIT es "CF", se permite duplicidad.
     * @return true si la actualización es exitosa, false si no se encuentra el cliente.
     */
    public boolean updateClientNit(String name, String newNit) {
        for (Client c : clients) {
            if (c.getName().equalsIgnoreCase(name)) {
                c.setNit(newNit);
                return true;
            }
        }
        return false; // Cliente no encontrado
    }
    /**
     * Obtener los datos de los clientes en formato de matriz para su uso en tablas.
     * @return Una matriz con los datos de los clientes.
     */
    public Object[][] getClientsData() {
        Object[][] data = new Object[clients.size()][3];
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            data[i][0] = client.getName();
            data[i][1] = client.getNit();
            data[i][2] = client.getTotalPurchases();
        }
        return data;
    }


    /**
 * Eliminar un cliente por su nombre.
 * @param name Nombre del cliente que se desea eliminar.
 * @return true si el cliente se elimina correctamente, false si no se encuentra.
 */
public boolean removeClient(String name) {
    for (Client c : clients) {
        if (c.getName().equalsIgnoreCase(name)) {
            clients.remove(c);
            return true; // Cliente eliminado exitosamente
        }
    }
    return false; // Cliente no encontrado
}


}