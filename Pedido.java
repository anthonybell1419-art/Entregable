import java.util.*;

public class Pedido {

    private int id;
    private Cliente cliente;
    private String estado;
    private Date fechaCreacion;
    private List<DetallePedido> detalles;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = "BORRADOR";
        this.fechaCreacion = new Date();
        this.detalles = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getEstado() { return estado; }
    public Date getFechaCreacion() { return fechaCreacion; }

    public void agregarProducto(Producto producto, int cantidad)
            throws StockInsuficienteException {

        if (!estado.equals("BORRADOR"))
            throw new IllegalStateException("Pedido no editable");

        if (producto.getStock() < cantidad)
            throw new StockInsuficienteException("Stock insuficiente");

        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (DetallePedido d : detalles)
            subtotal += d.calcularSubtotal();
        return subtotal;
    }

    public double calcularDescuento() {
        return cliente.calcularDescuento(calcularSubtotal());
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public void confirmar()
            throws PedidoInvalidoException, StockInsuficienteException {

        if (detalles.isEmpty())
            throw new PedidoInvalidoException("Pedido vacío");

        for (DetallePedido d : detalles)
            d.getProducto().disminuirStock(d.getCantidad());

        estado = "CONFIRMADO";
    }

    public void cancelar() {

        if (estado.equals("CONFIRMADO")) {
            for (DetallePedido d : detalles)
                d.getProducto().aumentarStock(d.getCantidad());
        }

        estado = "CANCELADO";
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id +
               " | Cliente: " + cliente.getNombre() +
               " | Fecha: " + fechaCreacion +
               " | Estado: " + estado +
               " | Total: " + calcularTotal();
    }
}
