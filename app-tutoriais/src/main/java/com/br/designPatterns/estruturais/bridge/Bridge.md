# 🌉 Design Pattern Bridge em Java

O **Bridge** é um padrão estrutural que divide a lógica de negócio ou uma classe grande em duas hierarquias separadas: **abstração** e **implementação**, permitindo que elas evoluam independentemente.

---

# 🧠 Quando usar?

## ✅ Quando você quer dividir e organizar uma classe monolítica que tem muitas variantes de funcionalidade
Ex: Uma classe `Forma` que tem subclasses para cada cor (`QuadradoAzul`, `QuadradoVermelho`). O Bridge separa em `Forma` e `Cor`.

## ✅ Quando você precisa estender uma classe em várias dimensões ortogonais
Evita a explosão de classes cartesianas.

## ✅ Quando a implementação deve ser selecionada em tempo de execução

---

# ❌ Quando NÃO usar

- Quando o sistema é simples e não há probabilidade de múltiplas dimensões de variação.
- Quando adiciona complexidade desnecessária para apenas uma ou duas classes concretas.

---

# 🏗️ Estrutura

- **Abstraction**: Define a lógica de controle de alto nível. Mantém uma referência ao objeto `Implementor`.
- **Refined Abstraction**: Estende a Abstraction com variações de controle.
- **Implementor**: Interface comum para todas as implementações.
- **Concrete Implementor**: Implementações específicas.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── implementor/
             │    ├── Dispositivo.java      // Implementor
             │    ├── Tv.java               // Concrete Implementor
             │    └── Radio.java            // Concrete Implementor
             ├── abstraction/
             │    ├── ControleRemoto.java          // Abstraction
             │    └── ControleRemotoAvancado.java  // Refined Abstraction
             └── Main.java
```

---

# 💻 Exemplo

## Implementor (Dispositivo.java)
```java
public interface Dispositivo {
    boolean isEnabled();
    void enable();
    void disable();
    int getVolume();
    void setVolume(int percent);
}
```

## Concrete Implementor (Tv.java)
```java
public class Tv implements Dispositivo {
    private boolean on = false;
    private int volume = 30;

    public boolean isEnabled() { return on; }
    public void enable() { on = true; }
    public void disable() { on = false; }
    public int getVolume() { return volume; }
    public void setVolume(int vol) { this.volume = vol; }
}
```

## Abstraction (ControleRemoto.java)
```java
public class ControleRemoto {
    protected Dispositivo dispositivo;

    public ControleRemoto(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void togglePower() {
        if (dispositivo.isEnabled()) {
            dispositivo.disable();
        } else {
            dispositivo.enable();
        }
        System.out.println("Power toggled.");
    }
}
```

## Refined Abstraction (ControleRemotoAvancado.java)
```java
public class ControleRemotoAvancado extends ControleRemoto {

    public ControleRemotoAvancado(Dispositivo device) {
        super(device);
    }

    public void mute() {
        System.out.println("Mute ativado");
        dispositivo.setVolume(0);
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        Dispositivo tv = new Tv();
        
        // A ponte ocorre aqui: passamos a implementação (TV) para a abstração (Controle)
        ControleRemotoAvancado controle = new ControleRemotoAvancado(tv);
        
        controle.togglePower();
        controle.mute();
    }
}
```

---

# 🔥 Vantagens

- Cria classes e aplicações independentes de plataforma.
- O código cliente trabalha com abstrações de alto nível.
- Princípio Aberto/Fechado.