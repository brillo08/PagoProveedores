public class ProcesoPagoProveedores {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Simulación del proceso
        Proveedor proveedor1 = new Proveedor("Gentium Proveedor");
        Control control = new Control();
        Contabilidad contabilidad = new Contabilidad();

        // Inicio del proceso
        proveedor1.recibirOrdenCompra();

        // Entrega de pedido
        proveedor1.entregarPedido(scanner);

        // Presentación de factura
        proveedor1.presentarFactura(scanner);

        // Control verifica y sella la factura
        control.sellarFactura(proveedor1, scanner);

        // Control entrega la documentación a contabilidad
        control.entregarADepartamentoContabilidad(proveedor1, scanner);

        // Contabilidad revisa la documentación
        contabilidad.revisarDocumentacion(proveedor1, scanner);

        // Contabilidad emite cheque
        contabilidad.emitirCheque(proveedor1, scanner);

        // Contabilidad realiza la transferencia o verifica si es necesario realizar transferencia electrónica
        contabilidad.realizarTransferencia(proveedor1, scanner);
        contabilidad.verificarYRealizarTransferencia(proveedor1, scanner);

        scanner.close();
    }
}
