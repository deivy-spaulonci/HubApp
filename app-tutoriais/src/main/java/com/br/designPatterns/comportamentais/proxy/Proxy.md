# 🛡️ Design Pattern Proxy em Java

O **Proxy** é um padrão estrutural que fornece um **substituto ou placeholder** para outro objeto. Um proxy controla o acesso ao objeto original, permitindo que você execute algo antes ou depois que a solicitação chegue ao objeto original.

---

# 🧠 Quando usar?

## ✅ Lazy Initialization (Proxy Virtual)
Quando você tem um objeto pesado que não é necessário imediatamente. O proxy cria o objeto real apenas quando ele é solicitado pela primeira vez.

## ✅ Controle de Acesso (Proxy de Proteção)
Quando você quer que diferentes clientes tenham diferentes direitos de acesso ao objeto de serviço.

## ✅ Execução Remota (Proxy Remoto)
Quando o objeto de serviço está em um servidor diferente. O proxy lida com a comunicação de rede.

## ✅ Logging e Caching (Proxy de Log/Cache)
Para registrar chamadas de método ou armazenar em cache os resultados de operações custosas.

---

# ❌ Quando NÃO usar

- Quando o objeto de serviço já é simples e não há necessidade de controle de acesso, cache ou inicialização preguiçosa.
- A resposta do serviço não é um problema e não precisa de cache ou gerenciamento.

---

# 🏗️ Estrutura

- **Service Interface**: Interface comum para o `Service` e o `Proxy`, permitindo que o cliente trate o proxy como o objeto real.
- **Service**: A classe que contém a lógica de negócio real.
- **Proxy**: Mantém uma referência ao objeto `Service`. Ele pode criar o objeto de serviço, se necessário, e delegar o trabalho a ele.

---

# 📦 Estrutura de pastas
```
src/
 └── main/
     └── java/
         └── com/seuapp/
             ├── service/
             │    ├── DownloaderDeVideo.java      // Service Interface
             │    └── DownloaderDeVideoReal.java  // Service
             ├── proxy/
             │    └── ProxyDownloaderDeVideo.java // Proxy
             └── Main.java
```

---

# 💻 Exemplo

Vamos criar um proxy de cache para um serviço de download de vídeos do YouTube.

## Service Interface (DownloaderDeVideo.java)
```java
public interface DownloaderDeVideo {
    String getVideo(String videoId);
}
```

## Service (DownloaderDeVideoReal.java)
```java
public class DownloaderDeVideoReal implements DownloaderDeVideo {
    @Override
    public String getVideo(String videoId) {
        System.out.println("Baixando vídeo " + videoId + " do YouTube...");
        // Lógica de download real
        return "Conteúdo do vídeo " + videoId;
    }
}
```

## Proxy (ProxyDownloaderDeVideo.java)
```java
import java.util.HashMap;
import java.util.Map;

public class ProxyDownloaderDeVideo implements DownloaderDeVideo {
    private DownloaderDeVideoReal downloaderReal;
    private Map<String, String> cache = new HashMap<>();

    public ProxyDownloaderDeVideo() {
        this.downloaderReal = new DownloaderDeVideoReal();
    }

    @Override
    public String getVideo(String videoId) {
        if (!cache.containsKey(videoId)) {
            // Se não está no cache, delega para o objeto real
            cache.put(videoId, downloaderReal.getVideo(videoId));
        } else {
            System.out.println("Retornando vídeo " + videoId + " do cache.");
        }
        return cache.get(videoId);
    }
}
```

## Uso (Main.java)
```java
public class Main {
    public static void main(String[] args) {
        DownloaderDeVideo downloader = new ProxyDownloaderDeVideo();

        downloader.getVideo("video1"); // Baixa do YouTube
        downloader.getVideo("video1"); // Retorna do cache
    }
}
```