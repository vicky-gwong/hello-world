import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static HashMap<String, String> mapaDoctores = new HashMap<>();
    private static HashMap<String, String> mapaPacientes = new HashMap<>();
    private static HashMap<String, String> mapaCitas = new HashMap<>();

    static List<Doctor> listaDoctores = new ArrayList<>();
    static List<Pacientes> listaPacientes = new ArrayList<>();
    static List<Citas> listaCitas = new ArrayList<>();

    static void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader("db/Doctores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    mapaDoctores.put(parts[0], parts[1]);
                    Doctor d = new Doctor(); d.nombre = parts[0]; d.especialidad = parts[1];
                    listaDoctores.add(d);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo de Doctores.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("db/Pacientes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    mapaPacientes.put(parts[0], parts[1]);
                    Pacientes p = new Pacientes(); p.nombre = parts[0]; p.ID = parts[1];
                    listaPacientes.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo de Pacientes.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("db/Citas.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    mapaCitas.put(parts[0], parts[2]);
                    Citas citas = new Citas(); citas.fecha = parts[0]; citas.doctor = parts[1]; citas.paciente = parts[2];
                    listaCitas.add(citas);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo de Citas.");
        }
    }

    static void guardarDatos() {
        try {
            BufferedWriter writerDoc = new BufferedWriter(new FileWriter("db/Doctores.txt"));
            for (Doctor d : listaDoctores) {
                writerDoc.write(d.nombre + "," + d.especialidad + "\n");
            }
            writerDoc.close();

            BufferedWriter writerPac = new BufferedWriter(new FileWriter("db/Pacientes.txt"));
            for (Pacientes p : listaPacientes) {
                writerPac.write(p.nombre + "," + p.ID + "\n");
            }
            writerPac.close();

            BufferedWriter writerCit = new BufferedWriter(new FileWriter("db/Citas.txt"));
            for (Citas c : listaCitas) {
                writerCit.write(c.fecha + "," + c.doctor + "," + c.paciente + "\n");
            }
            writerCit.close();
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    static boolean dentroDeHorarioLaboral(LocalDateTime dateTime) {
        DayOfWeek dia = dateTime.getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) return false;
        LocalTime hora = dateTime.toLocalTime();
        LocalTime inicio = LocalTime.of(8,0);
        LocalTime fin = LocalTime.of(18,0);
        return !hora.isBefore(inicio) && !hora.isAfter(fin);
    }

    public static void main(String[] args) {
        cargarDatos();
        String USUARIO_DEFAULT = "Victoria";
        String PASSWORD_DEFAULT = "1234";

        Scanner entrada = new Scanner(System.in);
        Usuario usuario = new Usuario();

        int selección = 0;
        System.out.println("Ingrese usuario");
        usuario.usuario = entrada.nextLine();
        System.out.println("Ingrese contraseña");
        usuario.password = entrada.nextLine();

        if(usuario.esDatosValidos(USUARIO_DEFAULT,PASSWORD_DEFAULT)){
            System.out.println("** Bienvenido al Sistema de Citas **");
            while (selección !=7){
                menu();
                try {
                    selección = Integer.parseInt(entrada.nextLine());
                    realizarAccion(selección);
                } catch (Exception e) {
                    System.out.println("Opción no válida.");
                }
            }
            guardarDatos();
        }else {
            System.out.println("Error en credencial");
        }
    }

    static void menu(){
        System.out.println("---- Menú de opciones ----");
        System.out.println("1. Alta de doctores");
        System.out.println("2. Alta de pacientes");
        System.out.println("3. Crear cita");
        System.out.println("4. Mostrar doctores");
        System.out.println("5. Mostrar pacientes");
        System.out.println("6. Mostrar citas");
        System.out.println("7. Salir");
        System.out.println("-- Digite un opción del menú --");
    }

    static void realizarAccion(Integer selección){
        switch (selección) {
            case 1: realizarAltaDoctores();
                break;
            case 2: realizarAltaPacientes();
                break;
            case 3: crearCita();
                break;
            case 4: mostrarDoctores();
                break;
            case 5: mostrarPacientes();
                break;
            case 6: mostrarCitas();
                break;
            case 7: System.out.println("Saliendo...");
                break;
        }
    }

    static void realizarAltaDoctores(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("-- Alta de doctores --");
        Doctor doctor = new Doctor();
        System.out.println("Nombre completo: ");
        doctor.nombre = entrada.nextLine();
        System.out.println("Especialidad: ");
        doctor.especialidad = entrada.nextLine();
        listaDoctores.add(doctor);
        System.out.println("Dr. "+doctor.nombre+" Registrado con éxito.");
        System.out.println("     ");
    }

    static void realizarAltaPacientes(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("-- Alta de pacientes --");
        Pacientes paciente = new Pacientes();
        System.out.println("Nombre completo: ");
        paciente.nombre = entrada.nextLine();
        System.out.println("ID: ");
        paciente.ID = entrada.nextLine();
        listaPacientes.add(paciente);
        System.out.println("Paciente: "+ paciente.nombre + " Registrado con éxito.");
        System.out.println("     ");
    }

    static void crearCita(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("-- Crear Cita --");
        Citas citas = new Citas();
        System.out.println("Seleccione una Fecha: (dd/MM/yyyy HH:mm)");
        String fechaTexto = entrada.nextLine();
        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaTexto, FORMATO);
            if (!dentroDeHorarioLaboral(fechaHora)) {
                System.out.println("Error: Cita fuera de horario laboral (Lun-Vie 08:00 a 18:00).");
                return;
            }
            citas.fecha = fechaTexto;
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato inválido. Ejemplo: 27/01/2026 14:30");
            return;
        }
        System.out.println("Seleccione un doctor o especialidad: ");
        citas.doctor = entrada.nextLine();
        System.out.println("Nombre del paciente: ");
        citas.paciente = entrada.nextLine();
        listaCitas.add(citas);
        System.out.println("Cita registrada con éxito.");
        System.out.println("     ");
    }

    static void mostrarDoctores(){
        System.out.println("-- Mostrar Doctor --");
        for (Doctor doctor : listaDoctores){
            System.out.println("Nombre: Dr."+doctor.nombre);
            System.out.println("Especialidad: "+doctor.especialidad);
            System.out.println("     ");
        }
    }

    static void mostrarPacientes(){
        System.out.println("-- Mostrar pacientes --");
        for (Pacientes paciente : listaPacientes){
            System.out.println("Paciente: "+paciente.nombre);
            System.out.println("ID: "+paciente.ID);
            System.out.println("----------");
        }
        System.out.println("     ");
    }

    static void mostrarCitas(){
        System.out.println("-- Mostrar citas --");
        for (Citas citas : listaCitas){
            System.out.println("Fecha: "+citas.fecha);
            System.out.println("Dr.: "+citas.doctor);
            System.out.println("Paciente: "+citas.paciente);
            System.out.println("----------");
        }
        System.out.println("     ");
    }
}