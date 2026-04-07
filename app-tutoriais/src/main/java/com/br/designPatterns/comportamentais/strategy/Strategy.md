# 🗺️ Design Pattern Strategy em Java

O **Strategy** é um padrão comportamental que permite definir uma família de algoritmos, colocar cada um deles em uma classe separada e tornar seus objetos intercambiáveis.

---

# 🧠 Quando usar?

## ✅ Quando você quer usar diferentes variantes de um algoritmo dentro de um objeto e ser capaz de trocar de um para o outro durante a execução
## ✅ Quando você tem muitas classes parecidas que só diferem na forma como executam algum comportamento

---

# 🏗️ Estrutura

- **Context**: Mantém referência para a Estratégia.
- **Strategy**: Interface comum para os algoritmos.
- **Concrete Strategies**: Implementações dos algoritmos.

---

# 💻 Exemplo

## Strategy Interface
```java
public interface PagamentoStrategy {
    void pagar(int valor);
}
```

## Concrete Strategies
```java
public class PagamentoCartao implements PagamentoStrategy {
    private String numero;
    public PagamentoCartao(String num) { this.numero = num; }
    
    @Override
    public void pagar(int valor) {
        System.out.println("Pago " + valor + " via Cartão de Crédito.");
    }
}

public class PagamentoPix implements PagamentoStrategy {
    @Override
    public void pagar(int valor) {
        System.out.println("Pago " + valor + " via PIX.");
    }
}
```

## Context (CarrinhoDeCompras.java)
```java
public class CarrinhoDeCompras {
    private PagamentoStrategy estrategia;

    public void setEstrategiaPagamento(PagamentoStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void checkout(int valor) {
        if (estrategia == null) {
            System.out.println("Selecione um método de pagamento.");
        } else {
            estrategia.pagar(valor);
        }
    }
}
```

## Uso
```java
public class Main {
    public static void main(String[] args) {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        
        carrinho.setEstrategiaPagamento(new PagamentoPix());
        carrinho.checkout(100);
        
        carrinho.setEstrategiaPagamento(new PagamentoCartao("1234-5678"));
        carrinho.checkout(250);
    }
}
```