# 🚦 Design Pattern Mediator em Java

O **Mediator** é um padrão comportamental que reduz as dependências caóticas entre objetos. O padrão restringe as comunicações diretas entre objetos e os força a colaborar apenas através de um objeto mediador.

---

# 🧠 Quando usar?

## ✅ Quando é difícil mudar algumas classes porque elas estão fortemente acopladas a várias outras classes
## ✅ Quando você não pode reutilizar uma classe em um programa diferente porque ela depende de muitas outras classes

---

# 🏗️ Estrutura

- **Mediator**: Interface de comunicação.
- **Concrete Mediator**: Coordena a comunicação entre os componentes.
- **Colleague**: Classes que se comunicam através do mediador.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── ChatMediator.java       // Mediator Interface
             ├── ChatRoom.java           // Concrete Mediator
             ├── User.java               // Colleague Abstract
             ├── UserImpl.java           // Concrete Colleague
             └── Main.java
```

---

# 💻 Exemplo

## Mediator & Concrete (ChatMediator.java, ChatRoom.java)
```java
public interface ChatMediator {
    void sendMessage(String msg, User user);
    void addUser(User user);
}

public class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public void sendMessage(String msg, User user) {
        for (User u : this.users) {
            // Mensagem não deve ser recebida pelo próprio remetente
            if (u != user) {
                u.receive(msg);
            }
        }
    }
}
```

## Colleague (User.java, UserImpl.java)
```java
public abstract class User {
    protected ChatMediator mediator;
    protected String name;

    public User(ChatMediator med, String name){
        this.mediator=med;
        this.name=name;
    }
    public abstract void send(String msg);
    public abstract void receive(String msg);
}

public class UserImpl extends User {
    public UserImpl(ChatMediator med, String name) { super(med, name); }

    @Override
    public void send(String msg){
        System.out.println(this.name + ": Enviando Msg=" + msg);
        mediator.sendMessage(msg, this);
    }
    @Override
    public void receive(String msg) {
        System.out.println(this.name + ": Recebida Msg=" + msg);
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatRoom();
        User user1 = new UserImpl(mediator, "Douglas");
        User user2 = new UserImpl(mediator, "Maria");
        
        mediator.addUser(user1);
        mediator.addUser(user2);
        
        user1.send("Olá pessoal!");
    }
}
```