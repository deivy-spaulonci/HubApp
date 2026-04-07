# 🚶 Design Pattern Visitor em Java

O **Visitor** é um padrão comportamental que permite separar algoritmos dos objetos nos quais eles operam.

---

# 🧠 Quando usar?

## ✅ Quando você precisa fazer uma operação em todos os elementos de uma estrutura de objetos complexa (ex: árvore)
## ✅ Quando você quer limpar a lógica de negócio de classes auxiliares
## ✅ Quando um comportamento faz sentido apenas para algumas classes de uma hierarquia

---

# 🏗️ Estrutura

- **Visitor**: Interface que declara métodos de visita para cada tipo de Elemento Concreto.
- **Concrete Visitor**: Implementa o comportamento.
- **Element**: Declara o método `accept`.
- **Concrete Element**: Implementa `accept` chamando o método correspondente do visitor.

---

# 💻 Exemplo

## Element Interface & Concrete Elements
```java
public interface Forma {
    void accept(Visitor visitor);
}

public class Circulo implements Forma {
    public double raio;
    public Circulo(double raio) { this.raio = raio; }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visitCirculo(this);
    }
}

public class Retangulo implements Forma {
    public double largura, altura;
    public Retangulo(double l, double a) { this.largura = l; this.altura = a; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRetangulo(this);
    }
}
```

## Visitor Interface & Concrete Visitor
```java
public interface Visitor {
    void visitCirculo(Circulo c);
    void visitRetangulo(Retangulo r);
}

public class ExportarXMLExportVisitor implements Visitor {
    @Override
    public void visitCirculo(Circulo c) {
        System.out.println("<circulo><raio>" + c.raio + "</raio></circulo>");
    }

    @Override
    public void visitRetangulo(Retangulo r) {
        System.out.println("<retangulo><w>" + r.largura + "</w><h>" + r.altura + "</h></retangulo>");
    }
}
```

## Uso
```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Forma> formas = new ArrayList<>();
        formas.add(new Circulo(10));
        formas.add(new Retangulo(5, 7));

        Visitor exportador = new ExportarXMLExportVisitor();

        for (Forma forma : formas) {
            forma.accept(exportador);
        }
    }
}
```

---

# 🔥 Vantagens

- Princípio Aberto/Fechado (adiciona operações sem mudar as classes).
- Agrupa operações relacionadas em uma única classe.