# 📝 Design Pattern Template Method em Java

O **Template Method** define o esqueleto de um algoritmo na superclasse, mas deixa as subclasses sobrescreverem etapas específicas do algoritmo sem alterar sua estrutura.

---

# 🧠 Quando usar?

## ✅ Quando você quer que os clientes estendam apenas etapas particulares de um algoritmo, mas não todo o algoritmo ou sua estrutura
## ✅ Quando você tem várias classes que contêm algoritmos quase idênticos com algumas diferenças menores

---

# 🏗️ Estrutura

- **Abstract Class**: Declara métodos que agem como etapas e o método principal (template method) que chama essas etapas.
- **Concrete Class**: Sobrescreve as etapas.

---

# 💻 Exemplo

## Abstract Class (ProcessadorDeDados.java)
```java
public abstract class ProcessadorDeDados {
    
    // O Template Method é final para não ser alterado
    public final void processar() {
        lerDados();
        processarDados();
        salvarDados();
    }

    protected abstract void lerDados();
    protected abstract void processarDados();
    
    // Hook: Método com implementação padrão que pode ser sobrescrito
    protected void salvarDados() {
        System.out.println("Salvando no Banco de Dados (Padrão)");
    }
}
```

## Concrete Class (ProcessadorCSV.java)
```java
public class ProcessadorCSV extends ProcessadorDeDados {
    @Override
    protected void lerDados() {
        System.out.println("Lendo arquivo CSV...");
    }

    @Override
    protected void processarDados() {
        System.out.println("Processando linhas do CSV...");
    }
}
```

## Concrete Class (ProcessadorPDF.java)
```java
public class ProcessadorPDF extends ProcessadorDeDados {
    @Override
    protected void lerDados() { System.out.println("Lendo PDF..."); }
    @Override
    protected void processarDados() { System.out.println("Extraindo texto do PDF..."); }
    @Override
    protected void salvarDados() { System.out.println("Enviando PDF por email (Override)"); }
}
```

## Uso
```java
public class Main {
    public static void main(String[] args) {
        ProcessadorDeDados proc = new ProcessadorCSV();
        proc.processar();
    }
}
```