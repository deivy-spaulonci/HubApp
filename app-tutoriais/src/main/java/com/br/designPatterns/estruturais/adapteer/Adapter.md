# 🔌 Design Pattern Adapter em Java

O **Adapter** é um padrão estrutural que permite que objetos com **interfaces incompatíveis colaborem**. Ele atua como um "tradutor" entre duas interfaces.

---

# 🧠 Quando usar?

## ✅ Quando você quer usar uma classe existente, mas sua interface não é compatível com o resto do seu código.
O Adapter envolve a classe existente com uma nova interface que o seu cliente entende.

## ✅ Quando você quer criar uma classe reutilizável que coopera com classes não relacionadas ou imprevistas.
Permite que classes que não poderiam trabalhar juntas por causa de interfaces incompatíveis o façam.

---

# ❌ Quando NÃO usar

- Quando as interfaces já são compatíveis.
- Quando é mais simples refatorar a classe existente para corresponder à interface necessária, em vez de criar um adaptador.

---

# 🏗️ Estrutura

- **Target**: A interface que o código cliente espera.
- **Adaptee**: A classe existente com uma interface incompatível que precisa ser adaptada.
- **Adapter**: Uma classe que envolve o `Adaptee` e implementa a interface `Target`. Ele traduz as chamadas do cliente para chamadas que o `Adaptee` entende.
- **Client**: O código que interage com os objetos através da interface `Target`.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── Pato.java         // Target
             ├── Peru.java         // Adaptee Interface
             ├── PeruSelvagem.java // Concrete Adaptee
             ├── PeruAdapter.java  // Adapter
             └── Main.java         // Client
```

---

# 💻 Exemplo

Vamos adaptar um `Peru` para que ele se comporte como um `Pato`.

## Target (Pato.java)
A interface que nosso cliente espera.
```java
public interface Pato {
    void grasnar();
    void voar();
}
```

## Adaptee (Peru.java & PeruSelvagem.java)
A classe que queremos adaptar.
```java
// Interface do Adaptee
public interface Peru {
    void gorgolejar();
    void voarCurto();
}

// Implementação concreta do Adaptee
public class PeruSelvagem implements Peru {
    @Override
    public void gorgolejar() {
        System.out.println("Gorgolejando!");
    }

    @Override
    public void voarCurto() {
        System.out.println("Voando uma curta distância");
    }
}
```

## Adapter (PeruAdapter.java)
O adaptador que faz o `Peru` se comportar como um `Pato`.
```java
public class PeruAdapter implements Pato {
    Peru peru;

    // O construtor recebe o objeto que estamos adaptando
    public PeruAdapter(Peru peru) {
        this.peru = peru;
    }

    // Traduz a chamada do método grasnar() para gorgolejar()
    @Override
    public void grasnar() {
        peru.gorgolejar();
    }

    // Um peru voa curtas distâncias, então adaptamos o voo
    @Override
    public void voar() {
        System.out.println("Adaptando o voo do peru...");
        for (int i = 0; i < 5; i++) {
            peru.voarCurto();
        }
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        PeruSelvagem peru = new PeruSelvagem();
        Pato peruAdapter = new PeruAdapter(peru);

        System.out.println("--- O Peru agindo como Peru ---");
        peru.gorgolejar();
        peru.voarCurto();

        System.out.println("\n--- O Peru adaptado agindo como Pato ---");
        testarPato(peruAdapter);
    }

    // Um método que só aceita Patos
    static void testarPato(Pato pato) {
        pato.grasnar();
        pato.voar();
    }
}
```

---

# 🔥 Vantagens

- **Reutilização de Código**: Permite usar classes existentes sem modificar seu código-fonte.
- **Desacoplamento**: O cliente não conhece o `Adaptee`, apenas a interface `Target`.
- **Princípio da Responsabilidade Única**: A lógica de conversão de interface fica isolada no Adapter.

---

# 🚀 Resumo

| Situação | Usar Adapter |
|--------------------------|--------------|
| Interfaces incompatíveis | ✅ Sim |
| Reutilizar classe legada | ✅ Sim |
| Interfaces já compatíveis | ❌ Não |