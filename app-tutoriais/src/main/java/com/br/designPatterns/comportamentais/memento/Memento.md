# 📸 Design Pattern Memento em Java

O **Memento** é um padrão comportamental que permite salvar e restaurar o estado anterior de um objeto sem revelar os detalhes de sua implementação.

---

# 🧠 Quando usar?

## ✅ Quando você quer produzir instantâneos (snapshots) do estado de um objeto para poder restaurar um estado anterior
## ✅ Quando o acesso direto aos campos do objeto viola o encapsulamento

---

# 🏗️ Estrutura

- **Originator**: O objeto cujo estado queremos salvar. Cria o Memento.
- **Memento**: Objeto de valor (Value Object) que armazena o estado do Originator.
- **Caretaker**: Gerencia os Mementos (histórico), mas não deve alterar seu conteúdo.

---

# 💻 Exemplo

## Memento (TextoMemento.java)
```java
public class TextoMemento {
    private final String estado;

    public TextoMemento(String estado) {
        this.estado = estado;
    }

    public String getEstadoSalvo() {
        return estado;
    }
}
```

## Originator (EditorTexto.java)
```java
public class EditorTexto {
    private String texto;

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() { return texto; }

    public TextoMemento salvar() {
        return new TextoMemento(texto);
    }

    public void restaurar(TextoMemento memento) {
        this.texto = memento.getEstadoSalvo();
    }
}
```

## Caretaker (Historico.java)
```java
import java.util.Stack;

public class Historico {
    private Stack<TextoMemento> pilha = new Stack<>();

    public void adicionar(TextoMemento m) {
        pilha.push(m);
    }

    public TextoMemento getUltimo() {
        return pilha.pop();
    }
}
```

## Uso
```java
public class Main {
    public static void main(String[] args) {
        EditorTexto editor = new EditorTexto();
        Historico historico = new Historico();

        editor.setTexto("Versão 1");
        historico.adicionar(editor.salvar());

        editor.setTexto("Versão 2");
        
        editor.restaurar(historico.getUltimo());
        System.out.println(editor.getTexto()); // Imprime "Versão 1"
    }
}
```