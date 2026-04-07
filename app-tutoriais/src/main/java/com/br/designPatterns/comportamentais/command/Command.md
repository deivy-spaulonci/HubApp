# 🎮 Design Pattern Command em Java

O **Command** é um padrão comportamental que transforma um pedido em um objeto independente contendo toda a informação sobre o pedido.

---

# 🧠 Quando usar?

## ✅ Quando você quer parametrizar objetos com operações
## ✅ Quando você quer enfileirar operações, agendar sua execução ou executá-las remotamente
## ✅ Quando você quer implementar operações reversíveis (Undo/Redo)

---

# ❌ Quando NÃO usar

- Quando o código é simples o suficiente para chamadas diretas de método.

---

# 🏗️ Estrutura

- **Command**: Interface com método `execute()`.
- **Concrete Command**: Implementa `execute` chamando métodos do `Receiver`.
- **Receiver**: O objeto que sabe fazer o trabalho real.
- **Invoker**: Inicia o comando (ex: um botão).

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── commands/
             │    ├── Command.java
             │    └── LightOnCommand.java
             ├── receiver/
             │    └── Light.java
             ├── invoker/
             │    └── RemoteControl.java
             └── Main.java
```

---

# 💻 Exemplo

## Command Interface & Concrete (Command.java, LightOnCommand.java)
```java
public interface Command {
    void execute();
}

public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}
```

## Receiver (Light.java)
```java
public class Light {
    public void on() {
        System.out.println("A luz está ligada");
    }
    public void off() {
        System.out.println("A luz está desligada");
    }
}
```

## Invoker (RemoteControl.java)
```java
public class RemoteControl {
    Command slot;

    public void setCommand(Command command) {
        this.slot = command;
    }

    public void buttonWasPressed() {
        slot.execute();
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);

        remote.setCommand(lightOn);
        remote.buttonWasPressed();
    }
}
```

---

# 🔥 Vantagens

- Desacopla classes que invocam operações das classes que as realizam.
- Permite implementar desfazer/refazer.
- Permite criar comandos compostos (macros).