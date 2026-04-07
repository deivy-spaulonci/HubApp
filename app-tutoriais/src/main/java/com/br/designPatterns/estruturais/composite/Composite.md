# 🌳 Design Pattern Composite em Java

O **Composite** é um padrão estrutural que permite compor objetos em **estruturas de árvore** e trabalhar com essa estrutura como se fosse um objeto individual.

---

# 🧠 Quando usar?

## ✅ Quando você precisa implementar uma estrutura de objetos tipo árvore
Ex: Sistemas de arquivos (pastas e arquivos), menus aninhados, elementos gráficos.

## ✅ Quando você quer que o código cliente trate objetos simples e compostos uniformemente
O cliente não precisa saber se está chamando um método em um item único ou em um grupo de itens.

---

# ❌ Quando NÃO usar

- Quando os componentes são muito diferentes entre si e não compartilham uma interface comum lógica.
- Quando criar a interface comum torna o sistema excessivamente genérico ou inseguro (ex: métodos que não fazem sentido para "folhas").

---

# 🏗️ Estrutura

- **Component**: Interface comum para todos os elementos da árvore.
- **Leaf (Folha)**: Elemento individual que não tem filhos. Faz o trabalho real.
- **Composite**: Elemento que tem filhos (outros componentes). Delega o trabalho para os filhos.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── ComponenteGrafico.java  // Component
             ├── Ponto.java              // Leaf
             ├── Circulo.java            // Leaf
             ├── GraficoComposto.java    // Composite
             └── Main.java
```

---

# 💻 Exemplo

## Component (ComponenteGrafico.java)
```java
public interface ComponenteGrafico {
    void desenhar();
    void mover(int x, int y);
}
```

## Leaf (Ponto.java)
```java
public class Ponto implements ComponenteGrafico {
    private int x, y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void desenhar() {
        System.out.println("Ponto em (" + x + ", " + y + ")");
    }

    @Override
    public void mover(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
```

## Composite (GraficoComposto.java)
```java
import java.util.ArrayList;
import java.util.List;

public class GraficoComposto implements ComponenteGrafico {
    private List<ComponenteGrafico> filhos = new ArrayList<>();

    public void adicionar(ComponenteGrafico grafico) {
        filhos.add(grafico);
    }

    public void remover(ComponenteGrafico grafico) {
        filhos.remove(grafico);
    }

    @Override
    public void desenhar() {
        System.out.println("Iniciando desenho composto:");
        for (ComponenteGrafico filho : filhos) {
            filho.desenhar();
        }
        System.out.println("Fim desenho composto.");
    }

    @Override
    public void mover(int x, int y) {
        for (ComponenteGrafico filho : filhos) {
            filho.mover(x, y);
        }
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        // Folhas
        Ponto p1 = new Ponto(1, 2);
        Ponto p2 = new Ponto(3, 4);
        
        // Composto menor
        GraficoComposto subGrupo = new GraficoComposto();
        subGrupo.adicionar(p1);

        // Composto principal
        GraficoComposto grupoPrincipal = new GraficoComposto();
        grupoPrincipal.adicionar(subGrupo);
        grupoPrincipal.adicionar(p2);

        // Cliente trata tudo igual
        grupoPrincipal.desenhar();
        grupoPrincipal.mover(2, 2);
    }
}
```

---

# 🔥 Vantagens

- Facilita a criação de estruturas complexas de objetos.
- Código cliente simples (polimorfismo recursivo).
- Facilidade para adicionar novos tipos de componentes.