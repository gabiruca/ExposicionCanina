/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.util.Objects;

/**
 *
 * @author gecarri
 */
public class Perro implements Comparable<Perro>{
    private String nombre;
    private String raza;
    private String imagen;
    private Integer points;
   

    public Perro(String nombre, String raza, String imagen) {
        this.nombre = nombre;
        this.raza = raza;
        this.imagen = imagen;
      
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

  
    
    public String toString(){
        return nombre + "(" + raza + ")";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nombre);
        hash = 37 * hash + Objects.hashCode(this.raza);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Perro other = (Perro) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.raza, other.raza)) {
            return false;
        }
        return true;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
    
    @Override
    public int compareTo(Perro p){
        return (nombre.compareTo(p.getNombre()));
    }
    



//Respuesta a la pregunta
//Si no se implementa Comparable al hacer el ordenamiento da un error de compilación porque no tiene como comparar los atributos, ya que el metodo compareTo le pertenece a la interface Comparable.
    
}
