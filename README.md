# flash-messages
_An easy way to send &amp; show *flash messages*_

|Build| State |
|--------|--------|
|Version|0.1.0-SNAPSHOT|
|Status |[![Build Status](https://travis-ci.org/jeslopalo/flash-messages.svg?branch=0.1.0)](https://travis-ci.org/jeslopalo/flash-messages)     |
|Coverage |[![Coverage Status](https://coveralls.io/repos/jeslopalo/flash-messages/badge.png?branch=0.1.0)](https://coveralls.io/r/jeslopalo/flash-messages?branch=0.1.0)|
|Coverity Scan |[![Coverity Scan Build Status](https://scan.coverity.com/projects/2142/badge.svg?branch=0.1.0)](https://scan.coverity.com/projects/2142?branch=0.1.0)|
|Project|[![Project Stats](https://www.ohloh.net/p/flash-messages/widgets/project_thin_badge.gif)](https://www.ohloh.net/p/flash-messages) |

# Flash!
When applying the [Post/Redirect/Get](http://kcy.me/15fxw) pattern in web application development, I run always into the same problem: __how to communicate the result to the user after the redirection__.

While it is a known problem and it has been resolved in other platforms (like Ruby), Java does not seem to provide a simple and elegant solution.

*flash-messages* is an easy way to communicate flash messages after a redirection in Java web applications.

Today, you can use *flash-messages* in applications which use **spring-mvc** as web framework and **Jstl** to render views. 

In future releases, it will be possible to use it in **JEE** applications and possibly with another view technologies like **Thymeleaf** or **Freemarker**.

Let's start!

## Features
- Seamless integration with ```@RequestMapping``` and ```@ExceptionHandler``` methods in the **spring-mvc** framework ```@Controller```'s
- Different levels of messages (ie. __SUCCESS__, __INFO__, __WARNING__, __ERROR__) 
- Resolution of __i18n__ messages with arguments
- Resolution of __i18n__ arguments (ie TextArgument, LinkArgument) 
- Easy integration with the **Twitter Bootstrap** alerts
 

## Getting started

### Get it into your project

#### Maven

##### Bill Of Materials (BOM)
*flash-messages* artifacts are in **Maven Central** and includes a BOM ([Bill Of Materials](http://kcy.me/15g1b)) to facilitate the use of its modules.
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

##### Artifacts
After importing the *BOM* in your `pom.xml` you can easily declare the modules.
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

#### Download
You can download the latest version directly from GitHub:

 - `flash-messages-core`        **(_[0.1.0-SNAPSHOT](http://)_)**
 - `flash-messages-spring`      **(_[0.1.0-SNAPSHOT](http://)_)**
 - `flash-messages-taglibs`     **(_[0.1.0-SNAPSHOT](http://)_)**

#### Building from sources
You can build the latest version directly from source. Just run:
```sh
$ mkdir flash-messages-repository
$ cd flash-messages-repository
$ git clone https://github.com/jeslopalo/flash-messages.git
$ cd flash-messages
$ mvn clean package
```

### Configuration
*flash-messages* is configured using **spring** [JavaConfig](http://kcy.me/15fuu). It has been tested with versions greater or equal than **3.2.6.RELEASE**.

#### Default configuration
In order to obtain the default configuration, just add ```@EnableFlashMessages``` to a ```@Configuration``` class (the same with ```@EnableWebMvc``` should be enough).
```java
import es.sandbox.ui.messages.spring.config.annotation.EnableFlashMessages;
   
@Configuration
@EnableFlashMessages
@EnableWebMvc
public class DefaultMessagesConfigurer {
   
    @Bean
    public MessageSource messageSource() {      
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/messages");        
        return messageSource;
    }
}
```

#### Custom configuration
To modify the default behavior of *flash-messages* just extend ```MessagesConfigurerAdapter``` and override those methods that you want to customize.
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

The main elements that can be configured or customized are:  _levels of messages_, the _css classes applied to the levels_, the _strategy to resolve i18n messages_ or _modify the scope where messages are stored_.

### Writing messages
In order to write messages, just declare an argument of type ```Messages``` in the handler method (or in a ```@ExceptionHandler``` method), then you can add messages to the different levels.

```java
@RequestMapping(value="/target", method= RequestMethod.POST)
String post(Messages messages, @ModelAttribute FormBackingBean form, BindingResult bindingResult) {
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

@ExceptionHandler(ServiceException.class)
String handle(ServiceException exception, Messages messages) {
    messages.addError("messages.service-exception");
    return "somewhere";
}
```

### Painting messages
Finally, you must to include the ```<flash:messages />``` taglib in your views (or better in your decorator template).
```jsp
<%@ taglib prefix="flash" uri="http://sandbox.es/tags/flash-messages" %>

...
<flash:messages />
...

```
