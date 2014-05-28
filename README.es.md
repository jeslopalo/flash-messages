#flash-messages
_Una forma sencilla de enviar &amp; mostrar mensajes flash_

|Build| State |
|--------|--------|
|Version|0.1.0-SNAPSHOT|
|Status |[![Build Status](https://travis-ci.org/jeslopalo/flash-messages.svg?branch=0.1.0)](https://travis-ci.org/jeslopalo/flash-messages)     |
|Coverage |[![Coverage Status](https://coveralls.io/repos/jeslopalo/flash-messages/badge.png?branch=0.1.0)](https://coveralls.io/r/jeslopalo/flash-messages?branch=0.1.0)|
|Coverity Scan |[![Coverity Scan Build Status](https://scan.coverity.com/projects/2142/badge.svg?branch=0.1.0)](https://scan.coverity.com/projects/2142?branch=0.1.0)|
|Project|[![Project Stats](https://www.ohloh.net/p/flash-messages/widgets/project_thin_badge.gif)](https://www.ohloh.net/p/flash-messages) |

#Flash!
Al aplicar el patrón [Post/Redirect/Get](http://en.wikipedia.org/wiki/Post/Redirect/Get) en el desarrollo de aplicaciones web, siempre me topo con el problema de presentar el resultado al usuario en forma de mensaje despues de la redirección.

Si bien, es un problema conocido y resuelto en otras plataformas como Ruby, en Java no parece existir una solución sencilla y elegante.

*flash-messages* es una forma fácil de presentar mensajes flash tras una redirección en aplicaciones Java.

```java
@RequestMapping(value="/target", method= RequestMethod.POST)
public String post(Messages messages, @ModelAttribute FormBackingBean form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {        
        return "form";
    }
    
    Result result= this.service.doSomething(form.getValue());
    if (result.isSuccessful()) {
        messages.addSuccess("messages.success-after-post", result.getValue());
        return "redirect:/successful-target-after-post";
    }
    
    messages.addError("messages.error-in-service", form.getValue());
    return "redirect:/error-target-after-post";
}
```

Hoy es posible utilizar *flash-messages* en aplicaciones web basadas en **Spring MVC** y con vistas escritas en **JSTL**. En futuras releases será posible usarla en aplicaciones **JEE** standalone y con otras tecnologías de vista (ie. **Thymeleaf**, etc.).

```jsp
<%@ taglib prefix="flash" uri="http://sandbox.es/tags/flash-messages" %>

...
<flash:messages />
...
```

Dependiendo del stack tecnológico de tu aplicación, será necesario declarar unas dependencias u otras.

Empecemos.

## ¿Cómo empezar?
###Obtener las dependencias
####Maven
#####Bill Of Materials (BOM)
*flash-messages* incluye un BOM ([Bill Of Materials](http://howtodoinjava.com/2014/02/18/maven-bom-bill-of-materials-dependency/)) para facilitar el uso de sus modulos.

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
          <groupId>es.sandbox.ui.messages</groupId>
          <artifactId>flash-messages-bom</artifactId>
          <version>0.1.0-SNAPSHOT</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
#####Artefactos
Habiendo importado el *BOM* en tu `pom.xml` podrás declarar las dependencias de forma sencilla:

```xml
<dependencies>
    ...
    <dependency>
        <groupId>es.sandbox.ui.messages</groupId>
        <artifactId>flash-messages-core</artifactId>
    </dependency>
    <dependency>
        <groupId>es.sandbox.ui.messages</groupId>
        <artifactId>flash-messages-spring</artifactId>
    </dependency>
    <dependency>
        <groupId>es.sandbox.ui.messages</groupId>
        <artifactId>flash-messages-taglibs</artifactId>
    </dependency>
    ...
</dependencies>
```
####Descarga directa
Puedes descargar directamente la última versión desde GitHub: 

 - `flash-messages-core`        **(_[0.1.0-SNAPSHOT](http://)_)**
 - `flash-messages-spring`      **(_[0.1.0-SNAPSHOT](http://)_)**
 - `flash-messages-taglibs`     **(_[0.1.0-SNAPSHOT](http://)_)**

####Construir desde el código fuente
Para construir la última versión directamente desde el código e instalar las librerías en tu repositorio local puedes ejecutar:

```zsh
$ mkdir flash-messages-repository
$ cd flash-messages-repository
$ git clone https://github.com/jeslopalo/flash-messages.git
$ cd flash-messages
$ mvn clean install
```

###Configurando Spring MVC
*flash-messages* se configura en **spring-mvc** mediante [JavaConfig](http://kcy.me/15fuu). Aunque probablemente funcione con versiones a partir de la 3.0.X, *flash-messages* sólo ha sido probado con versiones iguales o superiores a la **3.2.6.RELEASE**.

####1.- Configuración mínima:
Para obtener la configuración por defecto tan sólo es necesario añadir ```@EnableFlashMessages``` a una clase ```@Configuration``` (por ejemplo la misma con ```@EnableWebMvc```. 
```java
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
   
@Configuration
@EnableFlashMessages
public class DefaultMessagesConfigurer {
   
    @Bean
    public MessageSource messageSource() {      
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/messages");        
        return messageSource;
    }
}
```
####2.- Configuración personalizada:
Para poder personalizar algunos elementos de *flash-messages* podemos extender ```MessagesConfigurerAdapter``` y sobreescribir aquella configuración que queramos personalizar.
```java
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.context.CssClassesByLevel;
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
import es.sandbox.ui.messages.spring.config.annotation.MessagesConfigurerAdapter;

@Configuration
@EnableFlashMessages
public class CustomMessagesConfigurer extends MessagesConfigurerAdapter {

    /**
     * Sets the styles of flash-messages to be compatible 
     * with twitter bootstrap alerts
     */
     @Override
     public void configureCssClassesByLevel(CssClassesByLevel cssClasses) {
        cssClasses.put(Level.ERROR, "alert alert-danger");
     }
}
```
Entre otras cosas se pueden configurar los niveles que serán presentados, la estrategia para resolver los mensajes o bien configurar una implementación propia de dónde se almacenan los mensajes.