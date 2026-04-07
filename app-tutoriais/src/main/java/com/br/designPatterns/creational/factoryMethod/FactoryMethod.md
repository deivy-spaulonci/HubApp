# 🏭 Design Pattern Factory Method em Java

O **Factory Method** é um padrão criacional que define uma interface para criar objetos, mas permite que **subclasses decidam qual classe instanciar**.

---

# 🧠 Quando usar?

## ✅ Quando a criação do objeto deve ser delegada para subclasses
Você não sabe exatamente qual objeto será criado em tempo de compilação.

## ✅ Quando deseja desacoplar o código da classe concreta

## ✅ Quando há variações de um mesmo tipo de objeto

---

# ❌ Quando NÃO usar

- Quando a criação é simples
- Quando não há variações de implementação

---

# 🏗️ Estrutura

- Creator (classe abstrata ou interface)
- Concrete Creator
- Product (interface)
- Concrete Product

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── factory/
             ├── product/
             └── app/
```

---

# 💻 Exemplo

## Produto (Transporte.java)
```java
public interface Transporte {
    void entregar();
}
```

## Produto concreto (Caminhao.java)
```java
public class Caminhao implements Transporte {
    public void entregar() {
        System.out.println("Entrega por caminhão");
    }
}
```

## Produto concreto (Navio.java)
```java
public class Navio implements Transporte {
    public void entregar() {
        System.out.println("Entrega por navio");
    }
}
```

---

## Creator (Logistica.java)
```java
public abstract class Logistica {

    public abstract Transporte criarTransporte();

    public void planejarEntrega() {
        Transporte t = criarTransporte();
        t.entregar();
    }
}
```

## Concrete Creator (LogisticaRodoviaria.java)
```java
public class LogisticaRodoviaria extends Logistica {
    public Transporte criarTransporte() {
        return new Caminhao();
    }
}
```

## Concrete Creator (LogisticaMaritima.java)
```java
public class LogisticaMaritima extends Logistica {
    public Transporte criarTransporte() {
        return new Navio();
    }
}
```

---

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {

        Logistica logistica;

        String tipo = "terra";

        if (tipo.equals("terra")) {
            logistica = new LogisticaRodoviaria();
        } else {
            logistica = new LogisticaMaritima();
        }

        logistica.planejarEntrega();
    }
}
```

---

# 🔥 Vantagens

- Desacoplamento
- Código flexível e extensível
- Segue o princípio aberto/fechado (OCP)

---

# 🚀 Resumo

| Situação | Usar Factory Method |
|----------|---------------------|
| Variações de objeto | ✅ Sim |
| Criação delegada | ✅ Sim |
| Código simples | ❌ Não |

