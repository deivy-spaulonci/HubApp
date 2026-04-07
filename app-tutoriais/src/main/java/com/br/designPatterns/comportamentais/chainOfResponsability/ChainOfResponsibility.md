# ⛓️ Design Pattern Chain of Responsibility em Java

O **Chain of Responsibility** é um padrão comportamental que permite passar solicitações por uma corrente de handlers. Ao receber uma solicitação, cada handler decide se a processa ou a passa para o próximo handler da corrente.

---

# 🧠 Quando usar?

## ✅ Quando o programa deve processar diferentes tipos de pedidos, mas a ordem ou os tipos exatos não são conhecidos de antemão
## ✅ Quando é necessário executar vários handlers em uma ordem específica
## ✅ Quando o conjunto de handlers e sua ordem devem mudar dinamicamente

---

# ❌ Quando NÃO usar

- Quando é essencial que a solicitação seja tratada por pelo menos um handler (a corrente pode terminar sem tratamento).

---

# 🏗️ Estrutura

- **Handler**: Interface que declara o método de tratamento.
- **Base Handler**: Classe opcional com o código boilerplate para gerenciar a referência ao próximo handler.
- **Concrete Handlers**: Contêm o código real de processamento.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── Middleware.java           // Handler Base
             ├── AutenticacaoMiddleware.java // Concrete Handler
             ├── PermissaoMiddleware.java    // Concrete Handler
             ├── Server.java               // Client/Context
             └── Main.java
```

---

# 💻 Exemplo

## Handler Base (Middleware.java)
```java
public abstract class Middleware {
    private Middleware proximo;

    public Middleware linkWith(Middleware proximo) {
        this.proximo = proximo;
        return proximo;
    }

    public boolean check(String email, String password) {
        if (proximo == null) {
            return true;
        }
        return proximo.check(email, password);
    }
}
```

## Concrete Handler (AutenticacaoMiddleware.java)
```java
public class AutenticacaoMiddleware extends Middleware {
    public boolean check(String email, String password) {
        if (!email.equals("admin@admin.com")) {
            System.out.println("Email inválido!");
            return false;
        }
        if (!password.equals("123456")) {
            System.out.println("Senha inválida!");
            return false;
        }
        return checkNext(email, password); // Chama o método do pai que chama o próximo
    }
    
    // Helper para chamar super.check
    private boolean checkNext(String e, String p) { return super.check(e, p); }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        Middleware chain = new AutenticacaoMiddleware();
        chain.linkWith(new PermissaoMiddleware()); // Supondo que exista

        // O servidor recebe a corrente e a executa
        chain.check("user@teste.com", "123");
    }
}
```

---

# 🔥 Vantagens

- Reduz o acoplamento entre remetente e destinatário.
- Flexibilidade para adicionar ou remover responsabilidades.
- Princípio da Responsabilidade Única.