# 🏭 Design Pattern Abstract Factory em Java

O **Abstract Factory** é um padrão criacional que fornece uma interface para criar **famílias de objetos relacionados**, sem especificar suas classes concretas.

---

# 🧠 Quando usar?

## ✅ Quando existem múltiplas famílias de objetos
Ex: Botões e Checkboxes para Windows e Linux

## ✅ Quando você quer garantir compatibilidade entre objetos
Elementos da mesma família funcionam juntos.

## ✅ Quando deseja desacoplar criação de objetos do uso

---

# ❌ Quando NÃO usar

- Sistema simples
- Poucas variações de objetos

---

# 🏗️ Estrutura

- Abstract Factory (interface)
- Concrete Factories
- Abstract Products
- Concrete Products

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

## Produto (Button.java)
```java
public interface Button {
    void render();
}
```

## Produto concreto (WindowsButton.java)
```java
public class WindowsButton implements Button {
    public void render() {
        System.out.println("Botão Windows");
    }
}
```

## Produto concreto (LinuxButton.java)
```java
public class LinuxButton implements Button {
    public void render() {
        System.out.println("Botão Linux");
    }
}
```

---

## Factory (GUIFactory.java)
```java
public interface GUIFactory {
    Button createButton();
}
```

## Factory concreta (WindowsFactory.java)
```java
public class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }
}
```

## Factory concreta (LinuxFactory.java)
```java
public class LinuxFactory implements GUIFactory {
    public Button createButton() {
        return new LinuxButton();
    }
}
```

---

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {

        GUIFactory factory;

        String os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new LinuxFactory();
        }

        Button button = factory.createButton();
        button.render();
    }
}
```

---

# 🔥 Vantagens

- Desacoplamento
- Facilita troca de famílias de objetos
- Código mais organizado

---

# 🚀 Resumo

| Situação | Usar Abstract Factory |
|----------|----------------------|
| Múltiplas famílias | ✅ Sim |
| Compatibilidade entre objetos | ✅ Sim |
| Simples demais | ❌ Não |

