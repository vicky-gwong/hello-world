import java.util.Scanner;
public class Principal {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        System.out.print("Nombre del estudiante: ");
        String nombre = leer.nextLine();
        float[] calificaciones = new float[5];
        for (int i = 0; i < 5; i++){
            System.out.print("Calificación" + (i + 1) + ": ");
            calificaciones[i] = leer.nextFloat();
            while (calificaciones[i] < 0 || calificaciones[i] > 100) {
                System.out.print("Ingresa calificación " + (i + 1) + " (0 a 100): ");
                calificaciones[i] = leer.nextFloat();
            } 
        }
    Alumno alumno = new Alumno(nombre, calificaciones);
    float promedio = alumno.calcularPromedio(calificaciones);
    char calFinal = alumno.obtenerCalificacionFinal(promedio);
    alumno.imprimirResultados(nombre, promedio, calFinal);
    leer.close();
    }
}