# 🐑 Design Pattern Prototype em Java

O **Prototype** é um padrão criacional que permite **copiar objetos existentes** sem fazer com que seu código dependa de suas classes.

---

# 🧠 Quando usar?

## ✅ Quando o código não deve depender das classes concretas
Evita acoplamento com classes específicas ao clonar objetos.

## ✅ Quando a criação direta é custosa
Se criar um objeto do zero consome muitos recursos (banco de dados, rede, processamento), clonar uma instância existente é mais eficiente.

## ✅ Quando deseja evitar explosão de subclasses
Em vez de criar uma subclasse para cada variação de configuração, você usa protótipos clonáveis com estados diferentes.

---

# ❌ Quando NÃO usar

- Quando a lógica de clonagem é muito complexa (ex: referências circulares ou objetos muito aninhados).
- Quando o objeto possui estados externos que não podem ser copiados facilmente.

---

# 🏗️ Estrutura

- **Prototype**: Interface ou classe abstrata que declara o método `clone`.
- **Concrete Prototype**: Implementa o método de clonagem.
- **Client**: Cria um novo objeto solicitando que o protótipo se clone.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── model/
             │    └── Shape.java
             └── app/
                  └── Main.java
```

---

# 💻 Exemplo

## Prototype (Shape.java)
```java
public abstract class Shape implements Cloneable {
    
    private String id;
    protected String type;

    public abstract void draw();

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Método clone sobrescrito para ser público
    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
```

## Concrete Prototype (Circle.java)
```java
public class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Desenhando um Círculo");
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        
        Circle circle = new Circle();
        circle.setId("1");

        // O objeto é clonado, não instanciado do zero
        Circle clonedCircle = (Circle) circle.clone();
        
        System.out.println("Original: " + circle.getType());
        System.out.println("Clone: " + clonedCircle.getType());
        
        // Verifica se são instâncias diferentes na memória
        System.out.println("São o mesmo objeto? " + (circle == clonedCircle)); // false
    }
}
```

---

# 🔥 Vantagens

- Criação de objetos desacoplada da classe concreta.
- Adição e remoção de "produtos" (protótipos) em tempo de execução.
- Alternativa à herança para configurações de objetos.

---

# 🚀 Resumo

| Situação | Usar Prototype |
|----------|----------------|
| Clonagem independente | ✅ Sim |
| Performance na criação | ✅ Sim |
| Referências circulares | ❌ Não |