import java.util.*;

public class Sistema {

    private List<Producto> productos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void iniciar() {

        while (true) {
            try {
                mostrarMenu();
                int op = Integer.parseInt(sc.nextLine());

                switch (op) {
                    case 1: registrarProducto(); break;
                    case 2: registrarCliente(); break;
                    case 3: crearPedido(); break;
                    case 4: agregarProductoAPedido(); break;
                    case 5: listarProductos(); break;
                    case 6: listarPedidos(); break;
                    case 7: cambiarEstadoPedido(); break;
                    case 0: return;
                    default: System.out.println("Opción inválida");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("--------------------------------");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n1. Registrar producto");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear pedido");
        System.out.println("4. Agregar producto a pedido");
        System.out.println("5. Listar productos");
        System.out.println("6. Listar pedidos");
        System.out.println("7. Cambiar estado pedido");
        System.out.println("0. Salir");
    }

    private void registrarProducto() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Producto p : productos)
            if (p.getId() == id)
                throw new IllegalArgumentException("ID duplicado");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());

        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());

        productos.add(new Producto(id, nombre, precio, stock));
    }

    private void registrarCliente() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("1-Regular 2-VIP: ");
        int tipo = Integer.parseInt(sc.nextLine());

        if (tipo == 1)
            clientes.add(new ClienteRegular(id, nombre));
        else {
            System.out.print("Descuento %: ");
            double desc = Double.parseDouble(sc.nextLine());
            clientes.add(new ClienteVIP(id, nombre, desc));
        }
    }

    private void crearPedido() {
        System.out.print("ID Pedido: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("ID Cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        Cliente cliente = buscarCliente(idCliente);
        pedidos.add(new Pedido(id, cliente));
    }

    private void agregarProductoAPedido()
            throws ProductoNoEncontradoException, StockInsuficienteException {

        System.out.print("ID Pedido: ");
        int idPedido = Integer.parseInt(sc.nextLine());

        Pedido pedido = buscarPedido(idPedido);

        System.out.print("ID Producto: ");
        int idProducto = Integer.parseInt(sc.nextLine());

        Producto producto = buscarProducto(idProducto);

        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        pedido.agregarProducto(producto, cantidad);
    }

    private void listarProductos() {
        for (Producto p : productos)
            System.out.println(p);
    }

    private void listarPedidos() {
        for (Pedido p : pedidos)
            System.out.println(p);
    }

    private void cambiarEstadoPedido()
            throws PedidoInvalidoException, StockInsuficienteException {

        System.out.print("ID Pedido: ");
        int id = Integer.parseInt(sc.nextLine());

        Pedido pedido = buscarPedido(id);

        System.out.print("1. Confirmar  2. Cancelar: ");
        int op = Integer.parseInt(sc.nextLine());

        if (op == 1)
            pedido.confirmar();
        else
            pedido.cancelar();
    }

    private Producto buscarProducto(int id)
            throws ProductoNoEncontradoException {

        for (Producto p : productos)
            if (p.getId() == id)
                return p;

        throw new ProductoNoEncontradoException("Producto no encontrado");
    }

    private Cliente buscarCliente(int id) {
        for (Cliente c : clientes)
            if (c.getId() == id)
                return c;

        throw new IllegalArgumentException("Cliente no encontrado");
    }

    private Pedido buscarPedido(int id) {
        for (Pedido p : pedidos)
            if (p.getId() == id)
                return p;

        throw new IllegalArgumentException("Pedido no encontrado");
    }
}
