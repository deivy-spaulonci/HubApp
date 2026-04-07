# 🚦 Design Pattern State em Java

O **State** é um padrão comportamental que permite que um objeto altere seu comportamento quando seu estado interno muda. Parece como se o objeto mudasse de classe.

---

# 🧠 Quando usar?

## ✅ Quando o comportamento de um objeto depende do seu estado e deve mudar em tempo de execução
## ✅ Quando você tem muitas condicionais (if/switch) que alteram o comportamento da classe dependendo de campos de estado

---

# 🏗️ Estrutura

- **Context**: Mantém uma referência a uma instância de `State`.
- **State**: Interface que encapsula o comportamento associado a um estado.
- **Concrete States**: Implementam o comportamento específico.

---

# 💻 Exemplo

## State Interface
```java
public interface EstadoDoPedido {
    void proximo(Pedido pedido);
    void anterior(Pedido pedido);
    void printStatus();
}
```

## Concrete States
```java
public class PedidoNovo implements EstadoDoPedido {
    @Override
    public void proximo(Pedido p) {
        p.setEstado(new PedidoPago());
    }
    @Override
    public void anterior(Pedido p) {
        System.out.println("O pedido está no estado inicial.");
    }
    @Override
    public void printStatus() { System.out.println("Pedido Novo criado. Aguardando pagamento."); }
}

public class PedidoPago implements EstadoDoPedido {
    @Override
    public void proximo(Pedido p) {
        p.setEstado(new PedidoEnviado());
    }
    @Override
    public void anterior(Pedido p) {
        p.setEstado(new PedidoNovo());
    }
    @Override
    public void printStatus() { System.out.println("Pedido Pago. Pronto para envio."); }
}
// ... Outros estados (PedidoEnviado, etc)
```

## Context (Pedido.java)
```java
public class Pedido {
    private EstadoDoPedido estado = new PedidoNovo();

    public void setEstado(EstadoDoPedido estado) {
        this.estado = estado;
    }

    public void proximoEstado() {
        estado.proximo(this);
    }
    
    public void printStatus() {
        estado.printStatus();
    }
}
```

## Uso
```java
public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido();
        pedido.printStatus(); // Novo
        pedido.proximoEstado();
        pedido.printStatus(); // Pago
    }
}
```