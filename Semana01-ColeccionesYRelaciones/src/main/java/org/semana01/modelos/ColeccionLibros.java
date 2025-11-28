package org.semana01.modelos;

import java.util.*;

public class ColeccionLibros {
    // ¿Qué tipo de colección es la más adecuada para almacenar los libros?

    private ArrayList<Libro> libros;

    public ColeccionLibros() {
        this.libros = new ArrayList<>();
    }

    // Crea los métodos solicitados en el enunciado del ejercicio

    // METODO PARA AGREGAR LIBROS A LA COLECCIÓN
    public void agregarLibro(Libro libro) {
        this.libros.add(libro);
    }

    // 1) METODO QUE DEVUELVE LA CANTIDAD DE LIBROS QUE TIENEN MÁS DE 500 PÁGINAS
    public int cantidadLibrosMas500Paginas() {
        int contador = 0;
        for (Libro libro : libros) {
            if (libro.getPaginas() > 500) {
                contador++;
            }
        }
        return contador;
    }

    // 2) METODO QUE DEVUELVE LA CANTIDAD DE LIBROS QUE TIENEN MENOS DE 300 PÁGINAS
    public int cantidadLibrosMenos300Paginas() {
        int contador = 0;
        for (Libro libro : libros) {
            if (libro.getPaginas() < 300){
                contador++;
            }
        }
        return contador;
    }

    // 3) METODO QUE LISTA LOS TÍTULOS DE LIBROS CON MÁS DE 500 PÁGINAS
    public ArrayList<String> listarLibrosMas500Paginas() {
        ArrayList<String> titulos = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getPaginas() > 500) {
                String titulo = libro.getTitulo();
                titulos.add(titulo);
            }
        }
        return titulos;
    }

    // 4) METODO QUE LISTA LOS TÍTULOS DE LOS TRES LIBROS CON EL MAYOR NÚMERO DE PÁGINAS
    public ArrayList<String> listarTresLibrosMasPaginas() {
        ArrayList<Libro> librosOrdenados = new ArrayList<>(this.libros);
        Collections.sort(librosOrdenados, new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l2.getPaginas() - l1.getPaginas();
            }
        });
        ArrayList<String> titulos = new ArrayList<>();
        int numLibros = Math.min(librosOrdenados.size(), 3);
        for (int i = 0; i < numLibros; i++) {
            titulos.add(librosOrdenados.get(i).getTitulo());
        }
        return titulos;
    }

    // 5) METODO QUE SUMA LAS PÁGINAS DE TODOS LOS LIBROS
    public int sumaTotalPaginas(){
        int suma = 0;
        for (Libro libro : libros) {
            suma += libro.getPaginas();
        }
        return suma;
    }

    // 6) METODO QUE LISTA LOS LIBROS QUE SUPERAN EL PROMEDIO DE PAGINAS
    public ArrayList<Libro> listarLibrosMasPaginasPromedio() {
        if (this.libros.isEmpty()) {
            return new ArrayList<>();
        }
        int totalPaginas = sumaTotalPaginas();
        double promedio = (double) totalPaginas / this.libros.size();
        ArrayList<Libro> librosSuperanPromedio = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getPaginas() > promedio) {
                librosSuperanPromedio.add(libro);
            }
        }
        return librosSuperanPromedio;
    }

    // 7) METODO QUE LISTA LOS AUTORES SIN REPETIR
    public HashSet<String> listarAutores() {
        HashSet<String> autoresSinRepetir = new HashSet<>();
        for (Libro libro : libros) {
            String autor = libro.getAutor();
            autoresSinRepetir.add(autor);
        }
        return autoresSinRepetir;
    }

    // 8) METODO QUE DEVUELVE EL LIBRO CON MÁS PÁGINAS
    public Libro libroMasPaginas() {
        if (libros.isEmpty()) {
            return null;
        }
        Libro libroMasGrande = libros.get(0);
        for (int i = 1; i < libros.size(); i++) {
            Libro libroActual = libros.get(i);
            if (libroActual.getPaginas() > libroMasGrande.getPaginas()) {
                libroMasGrande = libroActual;
            }
        }
        return libroMasGrande;
    }

    // 9) METODO QUE DEVUELVE UNA LISTA CON LOS TÍTULOS DE TODOS LOS LIBROS
    public ArrayList<String> listarTitulos() {
        ArrayList<String> titulos = new ArrayList<>();
        for (Libro libro : libros) {
            titulos.add(libro.getTitulo());
        }
        return titulos;
    }

    // METODO QUE LISTA LOS AUTORES CON MÁS DE UN LIBRO
    public ArrayList<String> listarAutoresConMasDeUnLibro() {
        HashMap<String, Integer> recuentoAutores = new HashMap<>();
        for (Libro libro : libros) {
            String autor = libro.getAutor();
            int recuentoActual = recuentoAutores.getOrDefault(autor, 0);
            recuentoAutores.put(autor, recuentoActual + 1);
        }
        ArrayList<String> autoresConMasDeUnLibro = new ArrayList<>();
        for (Map.Entry<String, Integer> entrada : recuentoAutores.entrySet()) {
            if (entrada.getValue() > 1) {
                autoresConMasDeUnLibro.add(entrada.getKey());
            }
        }
        return autoresConMasDeUnLibro;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }
}
