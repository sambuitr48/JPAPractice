package application;

import jakarta.persistence.EntityManager;
import models.Cliente;
import utils.JpaUtil;

import java.util.Scanner;

public class HibernateEditar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID que quiere editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            Cliente cliente = entityManager.find(Cliente.class, id);
            if (cliente == null) {
                System.out.println("Cliente no encontrado");
                return;
            }

            System.out.println("Cliente encontrado: " + cliente);

            System.out.print("Ingrese el nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese la forma de pago: ");
            String formaPago = scanner.nextLine();

            entityManager.getTransaction().begin();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setFormaPago(formaPago);
            entityManager.getTransaction().commit();

            System.out.println("El cliente quedó registrado como Id: " + cliente.getId() +
                    ", Nombre: " + cliente.getNombre() +
                    ", Apellido: " + cliente.getApellido() +
                    ", Forma de pago: " + cliente.getFormaPago() + ". Gracias");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            scanner.close();
        }
    }
}
