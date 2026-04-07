# 🔄 Design Pattern Iterator em Java

O **Iterator** é um padrão comportamental que permite percorrer elementos de uma coleção sem expor as representações subjacentes (lista, pilha, árvore, etc.).

---

# 🧠 Quando usar?

## ✅ Quando sua coleção tem uma estrutura complexa por baixo dos panos, mas você quer esconder essa complexidade do cliente
## ✅ Quando você quer reduzir a duplicação de código de travessia
## ✅ Quando você quer que o código seja capaz de percorrer diferentes estruturas de dados uniformemente

---

# 🏗️ Estrutura

- **Iterator**: Interface com métodos como `getNext`, `hasMore`.
- **Concrete Iterator**: Implementa o algoritmo de travessia.
- **Iterable Collection**: Interface que retorna um iterador.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── Iterator.java
             ├── ColecaoDeNomes.java // Iterable Collection
             └── Main.java
```

---

# 💻 Exemplo

## Iterator (Iterator.java)
```java
public interface Iterator {
    boolean hasNext();
    Object next();
}
```

## Concrete Collection & Iterator (ColecaoDeNomes.java)
```java
public class ColecaoDeNomes {
    public String names[] = {"Robert" , "John" ,"Julie" , "Lora"};

    public Iterator getIterator() {
        return new NameIterator();
    }

    // Inner class para o iterador concreto
    private class NameIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            if(index < names.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()) {
                return names[index++];
            }
            return null;
        }
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        ColecaoDeNomes namesRepository = new ColecaoDeNomes();

        for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
            String name = (String)iter.next();
            System.out.println("Nome: " + name);
        }
    }
}
```

---

# 🔥 Vantagens

- Princípio da Responsabilidade Única (limpa o código do cliente e das coleções).
- Princípio Aberto/Fechado (novos tipos de coleções e iteradores).
- Iteração paralela sobre a mesma coleção.