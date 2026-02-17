public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {

        nombre = nombre.trim();

        if (nombre.isEmpty())
            throw new IllegalArgumentException("Nombre inválido");

        if (precio <= 0)
            throw new IllegalArgumentException("Precio debe ser mayor que 0");

        if (stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void disminuirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > stock)
            throw new StockInsuficienteException("Stock insuficiente de " + nombre);
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        stock += cantidad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre +
               " | Precio: " + precio +
               " | Stock: " + stock;
    }
}
