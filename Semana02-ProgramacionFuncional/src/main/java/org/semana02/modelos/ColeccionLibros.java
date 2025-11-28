package org.semana02.modelos;

import java.util.*;
import java.util.stream.Collectors;

public class ColeccionLibros {

    private ArrayList<Libro> libros;

    public ColeccionLibros() {
        this.libros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        this.libros.add(libro);
    }

    // Crea los métodos solicitados en el enunciado del ejercicio

    //1. Obtener la cantidad de libros con más de 500 páginas
    public int cantidadLibrosMas500Paginas() {

        return (int) libros.stream()
                .filter(l -> l.getPaginas() > 500)
                .count();
    }

    //2. Obtener la cantidad de libros con menos de 300 páginas
    public int cantidadLibrosMenos300Paginas() {

        return (int) libros.stream()
                .filter(l -> l.getPaginas() < 300)
                .count();
    }

    //3. Listar el título de todos aquellos libros con más de 500 páginas.
    public List<String> listarLibrosMas500Paginas() {

        return libros.stream()
                .filter(l -> l.getPaginas() > 500)
                .map(Libro::getTitulo)
                .toList(); //collect(Collectors.toList());
    }

    //4. Obtener el título de los 3 libros con mayor número de páginas.
    public List<String> listarTresLibrosMasPaginas() {

        return libros.stream()
                .sorted((l1,l2) -> l2.getPaginas() - l1.getPaginas())
                .limit(3)
                .map(l -> l.getTitulo())
                .toList();
    }


    //5. Obtener la suma total de las páginas de todos los libros.
    public int sumaTotalPaginas() {

        return libros.stream()
                .map(Libro::getPaginas)
                .reduce(0, Integer::sum);
    }

    //6. Obtener todos aquellos libros que superen el promedio en cuanto a número de páginas se refiere.
    public List<Libro> listarLibrosMasPaginasPromedio() {

        double promedio = libros.stream()
                .mapToInt(Libro::getPaginas)
                .average()
                .getAsDouble();
        return libros.stream()
                .filter(l -> l.getPaginas() > promedio)
                .toList();
    }

    //7. Obtener los autores de todos los libros, sin repetir nombres de autores.
    public List<String> listarAutores() {

        return libros.stream()
                .map(Libro::getAutor)
                .distinct()
                .toList();
    }

    //8. Obtener el libro con mayor número de páginas.
    public Libro libroMasPaginas() {

        return libros.stream()
                .max(Comparator.comparingInt(Libro::getPaginas))
                .orElse(null);
    }

    //9. Obtener una colección con todos los títulos de los libros.
    public List<String> listarTitulos() {

        return libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.toList());
    }

    //10. Obtener los autores que tengan más de 1 libro listado.
    public List<String> listarAutoresConMasDeUnLibro() {

        return libros.stream()
                .collect(Collectors.groupingBy(Libro::getAutor, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
    }
}