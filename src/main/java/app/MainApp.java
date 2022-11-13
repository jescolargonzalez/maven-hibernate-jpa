package app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        Producto producto;
        EntityManager entity = JPAutil.getEntityManager();
        while (opcion!=5) {
            System.out.println("0. Listar Productos");
            System.out.println("1. Crear Producto");
            System.out.println("2. Buscar Producto por ID");
            System.out.println("3. Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Salir");
            System.out.println("Elija una opcion:");

            opcion = scanner.nextInt();
            switch (opcion) {
                case 0:
                    List<Producto> listProductos= new ArrayList<>();
                    Query query1 = entity.createQuery("SELECT p FROM Producto p");
                    listProductos = query1.getResultList();
                    for (Producto p : listProductos) {
                        System.out.println(p);
                    }
                    System.out.println();
                    break;
                case 1:
                    System.out.println("Digite el nombre del producto:");
                    producto = new Producto();
                    producto.setId(null);
                    scanner.nextLine();
                    producto.setNombre(scanner.nextLine());

                    System.out.println("Digite el precio del producto:");
                    producto.setPrecio(scanner.nextDouble());
                    //System.out.println(producto);
                    entity.getTransaction().begin();
                    entity.persist(producto);
                    entity.getTransaction().commit();
                    System.out.println("Producto registrado..");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("Digite el id del producto a buscar:");
                    producto = new Producto();
                    producto = entity.find(Producto.class, scanner.nextLong());
                    if (producto != null) {
                        System.out.println(producto);
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println("Producto no encontrado... Lista de productos completa");
                        List<Producto> listaProductos= new ArrayList<>();
                        Query query=entity.createQuery("SELECT p FROM Producto p");
                        listaProductos=query.getResultList();
                        for (Producto p : listaProductos) {
                            System.out.println(p);
                        }
                        System.out.println();
                    }

                    break;
                case 3:
                    System.out.println("Digite el id del producto a actualizar:");
                    producto = new Producto();
                    producto = entity.find(Producto.class, scanner.nextLong());
                    if (producto != null) {
                        System.out.println(producto);
                        System.out.println("Digite el nombre del producto:");
                        scanner.nextLine();
                        producto.setNombre(scanner.nextLine());
                        System.out.println("Digite el precio del producto:");
                        producto.setPrecio(scanner.nextDouble());
                        entity.getTransaction().begin();
                        entity.merge(producto);
                        entity.getTransaction().commit();
                        System.out.println("Producto actualizado..");
                        System.out.println();
                    } else {
                        System.out.println("Producto no encontrado....");
                        System.out.println();
                    }
                    break;
                case 4:
                    System.out.println("Digite el id del producto a eliminar:");
                    producto = new Producto();
                    producto = entity.find(Producto.class, scanner.nextLong());
                    if (producto != null) {
                        System.out.println(producto);
                        entity.getTransaction().begin();
                        entity.remove(producto);
                        entity.getTransaction().commit();
                        System.out.println("Producto eliminado...");
                    } else {
                        System.out.println("Producto no encontrado...");
                    }
                    break;
                case 5:
                    entity.close();
                    break;

                default:
                    System.out.println("Opción no válida\n");
                    break;
            }
        }
    }

}

