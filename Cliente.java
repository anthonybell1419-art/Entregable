public abstract class Cliente {

    private int id;
    private String nombre;

    public Cliente(int id, String nombre) {
        nombre = nombre.trim();
        if (nombre.isEmpty())
            throw new IllegalArgumentException("Nombre inválido");

        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public abstract double calcularDescuento(double subtotal);
}
