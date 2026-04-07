# 🧱 Design Pattern Builder em Java

O **Design Pattern Builder** é utilizado para **construir objetos complexos passo a passo**, principalmente quando eles possuem muitos atributos ou quando queremos evitar construtores longos e confusos.

---

# 🧠 Quando usar o Builder?

## ✅ Muitos parâmetros no construtor
```java
new Usuario("Deivy", "email@email.com", "123", 18, true, "Brasil");
```

## ✅ Muitos atributos opcionais

## ✅ Objetos imutáveis

## ✅ Melhor legibilidade
```java
Usuario user = new Usuario.Builder()
    .nome("Deivy")
    .email("email@email.com")
    .idade(18)
    .build();
```

---

# ❌ Quando NÃO usar

- Objetos simples
- Sem complexidade na criação

---

# 🏗️ Estrutura

- Classe principal
- Classe Builder
- Método build()

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── model/
             │    └── Usuario.java
             └── app/
                  └── Main.java
```

---

# 💻 Exemplo

## Usuario.java
```java
public class Usuario {

    private final String nome;
    private final String email;

    private Usuario(Builder builder) {
        this.nome = builder.nome;
        this.email = builder.email;
    }

    public static class Builder {
        private String nome;
        private String email;

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }
}
```

---

# 🚀 Vantagens

- Legível
- Flexível
- Escalável