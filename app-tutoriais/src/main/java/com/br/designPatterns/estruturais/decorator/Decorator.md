# 🎁 Design Pattern Decorator em Java

O **Decorator** é um padrão estrutural que permite adicionar novos comportamentos a objetos dinamicamente, colocando-os dentro de objetos "wrapper" especiais que contêm esses comportamentos.

---

# 🧠 Quando usar?

## ✅ Para adicionar responsabilidades a objetos individuais de forma dinâmica e transparente
Sem afetar outros objetos da mesma classe.

## ✅ Quando a herança não é uma boa opção
Evita a explosão de subclasses para cada combinação de funcionalidades. Por exemplo, em vez de `NotificadorComSms`, `NotificadorComFacebook`, `NotificadorComSmsEFacebook`, você decora um notificador base.

## ✅ Quando a responsabilidade de um objeto pode ser removida

---

# ❌ Quando NÃO usar

- Quando você só precisa estender o comportamento de uma classe inteira, e não de objetos individuais. A herança simples pode ser suficiente.
- Quando a simplicidade é crucial e você tem poucas variações de comportamento.

---

# 🏗️ Estrutura

- **Component**: Interface comum para o objeto que será decorado e para os decoradores.
- **Concrete Component**: A classe do objeto original que será decorado.
- **Base Decorator**: Classe abstrata que implementa a interface `Component` e mantém uma referência para um objeto `Component`. Delega todas as chamadas para o objeto envolvido.
- **Concrete Decorator**: Estende o `Base Decorator` e adiciona o comportamento extra.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── datasource/
             │    ├── DataSource.java             // Component
             │    └── FileDataSource.java         // Concrete Component
             ├── decorators/
             │    ├── DataSourceDecorator.java    // Base Decorator
             │    ├── EncryptionDecorator.java    // Concrete Decorator
             │    └── CompressionDecorator.java   // Concrete Decorator
             └── Main.java
```

---

# 💻 Exemplo

Vamos decorar uma fonte de dados (`DataSource`) com criptografia e compressão.

## Component (DataSource.java)
```java
public interface DataSource {
    void writeData(String data);
    String readData();
}
```

## Concrete Component (FileDataSource.java)
```java
public class FileDataSource implements DataSource {
    private String name;
    public FileDataSource(String name) { this.name = name; }
    public void writeData(String data) { System.out.println("Escrevendo dados no arquivo: " + data); }
    public String readData() { return "Dados lidos do arquivo"; }
}
```

## Base Decorator (DataSourceDecorator.java)
```java
public abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    public void writeData(String data) {
        wrappee.writeData(data);
    }

    public String readData() {
        return wrappee.readData();
    }
}
```

## Concrete Decorator (EncryptionDecorator.java)
```java
public class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) { super(source); }

    @Override
    public void writeData(String data) {
        System.out.println("Criptografando dados...");
        super.writeData("dados_criptografados");
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        DataSource source = new FileDataSource("meu_arquivo.dat");

        // O objeto original é "embrulhado" por um decorador de criptografia
        source = new EncryptionDecorator(source);

        // O objeto já decorado é "embrulhado" novamente por um decorador de compressão
        // source = new CompressionDecorator(source);

        source.writeData("salario:1000");
    }
}
```

---

# 🔥 Vantagens

- Extensão de comportamento sem criar novas subclasses.
- Adição/remoção de responsabilidades em tempo de execução.
- Combinação de múltiplos comportamentos.