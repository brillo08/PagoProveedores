package clase1;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;

class Proveedor {
    String nombre;
    boolean pedidoEntregado;
    boolean facturaSellada;
    boolean pagoRealizado;
    boolean firmaPedidoRecibido;
    LocalDate fechaChequeEmitido;

    
    public Proveedor(String nombre) {
        this.nombre = nombre;
        this.pedidoEntregado = false;
        this.facturaSellada = false;
        this.pagoRealizado = false;
        this.firmaPedidoRecibido = false;
        this.fechaChequeEmitido = null;
    }

    public void recibirOrdenCompra() {
        System.out.println(nombre + " ha recibido la orden de compra.");
    }

    public void entregarPedido(Scanner scanner) {
        System.out.print("¿El proveedor " + nombre + " ha entregado el pedido? (si/no): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            pedidoEntregado = true;
            firmaPedidoRecibido = true; // Recibe la firma de entrega
            System.out.println(nombre + " ha entregado el pedido.");
        } else {
            System.out.println(nombre + " no ha entregado el pedido.");
        }
    }

    public void presentarFactura(Scanner scanner) {
        if (pedidoEntregado) {
            System.out.print("¿Desea presentar la factura para revisión? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.println(nombre + " ha presentado la factura para revisión.");
            } else {
                System.out.println(nombre + " no ha presentado la factura.");
            }
        } else {
            System.out.println(nombre + " no puede presentar factura sin haber entregado el pedido.");
        }
    }
}

class Control {
    public void sellarFactura(Proveedor proveedor, Scanner scanner) {
        if (proveedor != null && proveedor.pedidoEntregado && proveedor.firmaPedidoRecibido) {
            System.out.print("¿Desea sellar la factura de " + proveedor.nombre + "? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                proveedor.facturaSellada = true;
                System.out.println("Factura sellada para " + proveedor.nombre);
            } else {
                System.out.println("La factura no ha sido sellada.");
            }
        } else {
            if (proveedor == null) {
                System.out.println("El proveedor es nulo.");
            } else if (!proveedor.pedidoEntregado) {
                System.out.println("No se puede sellar la factura porque no se ha entregado el pedido.");
            } else {
                System.out.println("No se puede sellar la factura sin la firma del pedido entregado.");
            }
        }
    }

    public void entregarADepartamentoContabilidad(Proveedor proveedor, Scanner scanner) {
        if (proveedor.facturaSellada) {
            System.out.print("¿Desea entregar la factura de " + proveedor.nombre + " a contabilidad? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.println("Factura de " + proveedor.nombre + " entregada a contabilidad.");
            } else {
                System.out.println("La factura no ha sido entregada a contabilidad.");
            }
        } else {
            System.out.println("No se puede entregar a contabilidad sin sellar la factura.");
        }
    }
}

class Contabilidad {
    private final LocalDateTime fechaActual = LocalDateTime.now();

    public void revisarDocumentacion(Proveedor proveedor, Scanner scanner) {
        if (proveedor.facturaSellada) {
            System.out.print("¿Desea aprobar la documentación de " + proveedor.nombre + "? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.println("Revisión completada. Factura de " + proveedor.nombre + " aprobada.");
            } else {
                System.out.println("Revisión de la factura fallida. Solicitar nueva firma de pedido.");
                proveedor.firmaPedidoRecibido = false; // La firma es rechazada y se requiere una nueva.
            }
        } else {
            System.out.println("No se puede aprobar la factura sin sello.");
        }
    }

    public void emitirCheque(Proveedor proveedor, Scanner scanner) {
        if (proveedor.facturaSellada) {
            if (esHorarioDePago()) {
                System.out.print("¿Desea emitir el cheque para " + proveedor.nombre + "? (si/no): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("si")) {
                    proveedor.pagoRealizado = true;
                    proveedor.fechaChequeEmitido = LocalDate.now();
                    System.out.println("Cheque emitido para " + proveedor.nombre);
                } else {
                    System.out.println("El cheque no ha sido emitido.");
                }
            } else {
                System.out.println("El cheque solo puede ser emitido los viernes de 4 a 6 pm.");
            }
        } else {
            System.out.println("No se puede emitir cheque sin aprobación de factura.");
        }
    }

    public void realizarTransferencia(Proveedor proveedor, Scanner scanner) {
        if (proveedor.pagoRealizado) {
            System.out.print("¿Desea realizar la transferencia a la cuenta del proveedor " + proveedor.nombre + "? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.println("Transferencia realizada a la cuenta del proveedor " + proveedor.nombre);
            } else {
                System.out.println("La transferencia no ha sido realizada.");
            }
        } else {
            System.out.print("No se puede realizar la transferencia sin haber emitido el cheque. ¿Desea continuar? (si/no): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("si")) {
                System.out.println("La transferencia no puede ser realizada sin el cheque emitido. Proceso finalizado.");
            } else {
                System.out.println("La transferencia no ha sido realizada.");
            }
        }
    }

    public void verificarYRealizarTransferencia(Proveedor proveedor, Scanner scanner) {
        if (proveedor.pagoRealizado) {
            // Verificar si han pasado 30 días desde la emisión del cheque
            LocalDate fechaLimite = proveedor.fechaChequeEmitido.plus(30, ChronoUnit.DAYS);
            if (LocalDate.now().isAfter(fechaLimite)) {
                System.out.println("El cheque no ha sido retirado en 30 días. Realizando transferencia electrónica.");
                System.out.println("Transferencia realizada a la cuenta del proveedor " + proveedor.nombre);
            } else {
                System.out.println("El cheque aún está dentro del periodo de 30 días para ser retirado.");
            }
        } else {
            System.out.println("No se puede realizar la transferencia sin haber emitido el cheque.");
        }
    }

    private boolean esHorarioDePago() {
        LocalTime horaActual = fechaActual.toLocalTime();
        DayOfWeek diaDeLaSemana = fechaActual.getDayOfWeek();
        return diaDeLaSemana == DayOfWeek.FRIDAY &&
               horaActual.isAfter(LocalTime.of(16, 0)) &&
               horaActual.isBefore(LocalTime.of(18, 0));
    }
}

