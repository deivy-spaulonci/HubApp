# 🍃 Design Pattern Flyweight em Java

O **Flyweight** é um padrão estrutural que permite que você encaixe mais objetos na RAM disponível, compartilhando partes comuns de estado entre múltiplos objetos em vez de manter todos os dados em cada objeto.

---

# 🧠 Quando usar?

## ✅ Quando um aplicativo precisa gerar um número enorme de objetos semelhantes
E a RAM se torna um problema.

## ✅ Quando a maior parte do estado de um objeto pode ser tornada extrínseca
Ou seja, o estado pode ser movido para fora do objeto e passado para seus métodos.

---

# ❌ Quando NÃO usar

- Quando os objetos não têm estados intrínsecos (compartilháveis) significativos.
- Quando o número de objetos é pequeno e o consumo de memória não é uma preocupação.

---

# 🏗️ Estrutura

- **Flyweight**: Interface que declara os métodos que recebem o estado extrínseco.
- **Concrete Flyweight**: Armazena o estado intrínseco (compartilhado).
- **Flyweight Factory**: Cria e gerencia os objetos Flyweight, garantindo o compartilhamento.
- **Context**: Armazena o estado extrínseco (único para cada objeto) e mantém uma referência ao Flyweight.
- **Client**: O cliente calcula ou armazena o estado extrínseco e o passa para os métodos do Flyweight.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── flyweight/
             │    ├── TipoArvore.java      // Flyweight
             │    └── FabricaDeArvores.java // Flyweight Factory
             ├── context/
             │    └── Arvore.java           // Context
             └── Main.java
```

---

# 💻 Exemplo

Vamos desenhar uma floresta com milhares de árvores. Cada árvore tem um tipo (modelo, cor - estado intrínseco) e uma posição (x, y - estado extrínseco).

## Flyweight (TipoArvore.java)
```java
import java.awt.Color;

// O objeto Flyweight contém a parte intrínseca (compartilhada) do estado.
public class TipoArvore {
    private final String nome;
    private final Color cor;

    public TipoArvore(String nome, Color cor) {
        this.nome = nome;
        this.cor = cor;
    }

    public void desenhar(int x, int y) {
        System.out.println("Desenhando árvore '" + nome + "' na cor " + cor + " em (" + x + ", " + y + ")");
    }
}
```

## Flyweight Factory (FabricaDeArvores.java)
```java
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class FabricaDeArvores {
    static Map<String, TipoArvore> tiposDeArvore = new HashMap<>();

    public static TipoArvore getTipoArvore(String nome, Color cor) {
        TipoArvore resultado = tiposDeArvore.get(nome);
        if (resultado == null) {
            resultado = new TipoArvore(nome, cor);
            tiposDeArvore.put(nome, resultado);
            System.out.println("Criando novo tipo de árvore: " + nome);
        }
        return resultado;
    }
}
```

## Context (Arvore.java)
```java
// O objeto de contexto armazena o estado extrínseco.
public class Arvore {
    private int x, y;
    private TipoArvore tipo;

    public Arvore(int x, int y, TipoArvore tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public void desenhar() {
        tipo.desenhar(x, y);
    }
}
```

## Uso (Main.java)
```java
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        // O cliente cria um objeto de contexto para cada árvore,
        // mas os objetos Flyweight (TipoArvore) são compartilhados.
        TipoArvore carvalho = FabricaDeArvores.getTipoArvore("Carvalho", Color.GREEN);
        Arvore arvore1 = new Arvore(10, 20, carvalho);
        Arvore arvore2 = new Arvore(30, 50, carvalho); // Reutiliza o mesmo Flyweight

        arvore1.desenhar();
        arvore2.desenhar();
    }
}
```