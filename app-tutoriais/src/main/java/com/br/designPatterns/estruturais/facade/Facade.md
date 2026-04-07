# 🏛️ Design Pattern Facade em Java

O **Facade** é um padrão estrutural que fornece uma **interface simplificada** para uma biblioteca, um framework ou qualquer outro conjunto complexo de classes.

---

# 🧠 Quando usar?

## ✅ Para fornecer uma interface simples para um subsistema complexo
Isso ajuda a reduzir a complexidade e as dependências do cliente com o subsistema.

## ✅ Para desacoplar um subsistema do código cliente
Se o subsistema mudar no futuro, apenas a fachada precisará ser modificada, não o código cliente.

## ✅ Para estruturar um subsistema em camadas
Crie fachadas para definir pontos de entrada para cada nível de um subsistema.

---

# ❌ Quando NÃO usar

- Quando a interface já é simples e não há necessidade de uma camada extra de abstração.
- O uso excessivo pode levar a "God Objects" (objetos que sabem e fazem demais), violando o Princípio da Responsabilidade Única.

---

# 🏗️ Estrutura

- **Facade**: Conhece quais classes do subsistema são responsáveis por uma requisição e delega o trabalho a elas.
- **Subsystem Classes**: Implementam a funcionalidade do subsistema. Elas não têm conhecimento da fachada.
- **Client**: Usa a fachada em vez de chamar os objetos do subsistema diretamente.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── subsistema/
             │    ├── Codec.java
             │    ├── BitrateReader.java
             │    └── AudioMixer.java
             ├── facade/
             │    └── VideoConversionFacade.java
             └── Main.java
```

---

# 💻 Exemplo

Vamos criar uma fachada para simplificar a conversão de um vídeo.

## Subsistema Complexo
```java
// Apenas classes de exemplo para o subsistema
class Codec { public String tipo = "mp4"; }
class BitrateReader { public void read(String file, Codec source) { /* ... */ } }
class AudioMixer { public void fix(String result) { /* ... */ } }
```

## Facade (VideoConversionFacade.java)
```java
import java.io.File;

public class VideoConversionFacade {
    public File convertVideo(String fileName, String format) {
        System.out.println("Iniciando conversão de vídeo...");
        
        // Lógica complexa do subsistema é encapsulada aqui
        Codec sourceCodec = new Codec();
        new BitrateReader().read(fileName, sourceCodec);
        // ... mais lógica
        
        System.out.println("Conversão concluída para o formato " + format);
        return new File("video_convertido." + format);
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        // O cliente não precisa conhecer as classes Codec, BitrateReader, etc.
        VideoConversionFacade converter = new VideoConversionFacade();
        File mp4Video = converter.convertVideo("meu_video.avi", "mp4");
    }
}
```

---

# 🔥 Vantagens

- Isola o código cliente da complexidade de um subsistema.
- Reduz o acoplamento entre o cliente e o subsistema.