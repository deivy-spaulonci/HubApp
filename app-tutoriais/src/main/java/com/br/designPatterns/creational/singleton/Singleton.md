# 💍 Design Pattern Singleton em Java

O **Singleton** é um padrão criacional que garante que uma classe tenha **apenas uma instância**, enquanto provê um ponto de acesso global para essa instância.

---

# 🧠 Quando usar?

## ✅ Controle estrito sobre variáveis globais
Diferente de variáveis globais, o Singleton garante que só haverá uma instância.

## ✅ Recursos compartilhados
Ex: Conexão com banco de dados, logs, drivers de sistema de arquivos.

---

# ❌ Quando NÃO usar

- Quando o estado global dificulta testes unitários (mocking é difícil).
- Quando causa acoplamento excessivo entre as classes.
- Em ambientes multithread sem o devido cuidado (pode gerar múltiplas instâncias se não for sincronizado).

---

# 🏗️ Estrutura

- Construtor privado (evita `new`).
- Campo estático privado contendo a única instância.
- Método estático público para acesso.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── config/
             │    └── DatabaseConnection.java
             └── app/
                  └── Main.java
```

---

# 💻 Exemplo

## Singleton (DatabaseConnection.java)
```java
public class DatabaseConnection {

    private static DatabaseConnection instance;
    
    // Construtor privado evita instanciação externa
    private DatabaseConnection() {
        System.out.println("Conectando ao banco...");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Executando: " + sql);
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.query("SELECT * FROM users");

        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        // db1 e db2 são a mesma instância
        System.out.println(db1 == db2); // true
    }
}
```

---

# 🔥 Vantagens

- Garante uma única instância.
- Acesso global a essa instância.
- Inicialização sob demanda (Lazy Loading).
