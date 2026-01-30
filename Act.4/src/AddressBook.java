import java.util.*;
import java.io.*;

public class AddressBook {
    private HashMap<String, String> contacts = new HashMap<>();
    private final String filename = "contacts.txt";
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {2
                    contacts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró contacto existente.");
        }
    }
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
            System.out.println("Cambios guardados con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar contacto: " + e.getMessage());
        }
    }
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    public void create(String number, String name) {
        contacts.put(number, name);
        System.out.println("Contacto agregado.");
    }
    public void delete(String number) {
        if (contacts.containsKey(number)) {
            contacts.remove(number);
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("Contacto inválido");
        }
    }
    public static void main(String[] args) {
        AddressBook agenda = new AddressBook();
        Scanner sn = new Scanner(System.in);
        agenda.load();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- AGENDA TELEFÓNICA ---");
            System.out.println("1. Listado");
            System.out.println("2. Crear");
            System.out.println("3. Eliminar");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(sn.nextLine());
                switch (option) {
                    case 1 -> agenda.list();
                    case 2 -> {
                        System.out.print("Ingrese el número: ");
                        String num = sn.nextLine();
                        System.out.print("Ingrese el nombre: ");
                        String nom = sn.nextLine();
                        agenda.create(num, nom);
                    }
                    case 3 -> {
                        System.out.print("Ingrese el número a eliminar: ");
                        String numDel = sn.nextLine();
                        agenda.delete(numDel);
                    }
                    case 4 -> {
                        agenda.save();
                        exit = true;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }
}