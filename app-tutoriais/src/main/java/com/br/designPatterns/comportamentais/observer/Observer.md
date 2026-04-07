# 📡 Design Pattern Observer em Java

O **Observer** é um padrão comportamental que permite definir um mecanismo de subscrição para notificar múltiplos objetos sobre quaisquer eventos que aconteçam com o objeto que eles estão observando.

---

# 🧠 Quando usar?

## ✅ Quando a mudança de estado de um objeto exige mudanças em outros objetos
## ✅ Quando alguns objetos devem observar outros apenas por um tempo limitado ou em casos específicos

---

# 🏗️ Estrutura

- **Subject (Publisher)**: Mantém a lista de dependentes (observers) e notifica-os.
- **Observer (Subscriber)**: Interface com o método de atualização.
- **Concrete Observer**: Reage à notificação.

---

# 💻 Exemplo

## Observer & Subject
```java
import java.util.ArrayList;
import java.util.List;

public interface Observer {
    void update(String mensagem);
}

public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyUpdate(String mensagem);
}
```

## Concrete Subject (CanalYoutube.java)
```java
public class CanalYoutube implements Subject {
    private List<Observer> inscritos = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        inscritos.add(o);
    }

    @Override
    public void detach(Observer o) {
        inscritos.remove(o);
    }

    @Override
    public void notifyUpdate(String mensagem) {
        for(Observer o: inscritos) {
            o.update(mensagem);
        }
    }
    
    public void uploadVideo(String titulo) {
        notifyUpdate("Novo vídeo: " + titulo);
    }
}
```

## Concrete Observer (Inscrito.java)
```java
public class Inscrito implements Observer {
    private String nome;
    
    public Inscrito(String nome) { this.nome = nome; }

    @Override
    public void update(String msg) {
        System.out.println(nome + " recebeu notificação: " + msg);
    }
}
```

## Uso
```java
public class Main {
    public static void main(String[] args) {
        CanalYoutube canal = new CanalYoutube();
        Inscrito s1 = new Inscrito("João");
        Inscrito s2 = new Inscrito("Maria");

        canal.attach(s1);
        canal.attach(s2);
        canal.uploadVideo("Padrão Observer Explicado");
    }
}
```