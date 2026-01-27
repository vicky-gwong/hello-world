public class Alumno {
    private String nombre;
    private float[] calificaciones;
    public Alumno(String nombre, float[] calificaciones) {
        this.nombre = nombre;
        this.calificaciones = calificaciones;
    }
    public float calcularPromedio(float[] calificaciones){
        float suma = 0;
        for (int i = 0; i < calificaciones.length; i++){
            suma += calificaciones[i];
        }
        return suma / calificaciones.length;
    }

public char obtenerCalificacionFinal(float promedio) {
    if (promedio <= 50) { return 'F';
    } else if (promedio <= 60) { return 'E';    
    } else if (promedio <= 70) { return 'D';
    } else if (promedio <= 80) { return 'C';
    } else if (promedio <= 90) { return 'B';
    } else { return 'A';
    }
}

public void imprimirResultados(String nombre, float promedio, char calificacion) {
    System.out.println("Nombre del estudiante: " + nombre);
    System.out.println("Calificación 1: " + calificaciones[0]);
    System.out.println("Calificación 2: " + calificaciones[1]);
    System.out.println("Calificación 3: " + calificaciones[2]);
    System.out.println("Calificación 4: " + calificaciones[3]);
    System.out.println("Calificación 5: " + calificaciones[4]);
    System.out.println("Promedio: " + promedio);
    System.out.println("Calificación: " + calificacion);
}

public String getNombre() {
    return nombre;}

public float[] getCalifaciones() {
    return calificaciones;}

}