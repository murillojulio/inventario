package com.murillojulio.inventario.common.pojo;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class Product {

    /*Constantes que son utiles para el paso de paramentros entre actividades y fragmentos*/
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String PHOTO_URL = "photoUrl";

    @Exclude /* Para que no mapee la propiedad id para la lectura o escritura dentro de real time database*/
    private String id;
    private String name;
    private int quantity;
    private String photoUrl;

    public Product() {
    }

    @Exclude /*La propiedad id unicamente va a ser de modo local*/
    public String getId() {
        return id;
    }

    @Exclude /*La propiedad id unicamente va a ser de modo local*/
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    /*
    En Java los métodos equals y hashCode están definidos en la raíz de la jerarquía de clases,
    esto es en la clase Object, lo que significa que todas las instancias de objetos los poseen.
    Estos métodos son especialmente importantes ya que afectan al correcto funcionamiento de las
    colecciones como Collection, List, Set y Map, colecciones, listas, conjuntos y mapas que es
    difícil que cualquier programa no use alguna implementación de ellas.

    El método equals es usado en las colecciones de tipo List, Set, y también Map para determinar
    si un objeto ya está incluida en la colección, el método hashCode es usado en los Map para
    encontrar el objeto asociado a la clave. Dado que las colecciones son ampliamente usadas en
    cualquier programa la correcta implementación de los métodos equals y hashCode
    es fundamental ya que de lo contrario descubriremos errores poco agradables.
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
