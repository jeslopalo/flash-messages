package es.sandbox.ui.messages.argument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import es.sandbox.ui.messages.resolver.MessageResolver;
import es.sandbox.ui.messages.resolver.Resolvable;


public class LinkArgument implements Link, Resolvable {

   private static final long serialVersionUID= 7002144539900854365L;

   private static final String LINK_FORMAT= "<a href=\"%s\" title=\"%s\" class=\"%s\">%s</a>";
   private static final String LINK_FORMAT_WITHOUT_CSS_CLASS= "<a href=\"%s\" title=\"%s\">%s</a>";

   private String url;
   private Text text;
   private String cssClass;


   public LinkArgument(String url) {
      url(url);
   }

   private void assertThatUrlIsValid(String url) {
      if (url == null) {
         throw new NullPointerException("Link url can't be null");
      }

      if (StringUtils.isBlank(url)) {
         throw new IllegalArgumentException("Link url can't be empty");
      }
   }

   @Override
   public LinkArgument url(String url) {
      assertThatUrlIsValid(url);
      this.url= url;
      return this;
   }

   @Override
   public LinkArgument title(Text text) {
      this.text= text;
      return this;
   }

   @Override
   public LinkArgument title(String code, Serializable... arguments) {
      this.text= new TextArgument(code, arguments);
      return this;
   }

   @Override
   public LinkArgument cssClass(String cssClass) {
      this.cssClass= StringUtils.trimToNull(cssClass);
      return this;
   }

   @Override
   public String resolve(MessageResolver messageResolver) {
      MessageResolver.assertThatIsNotNull(messageResolver);

      return String.format(linkFormat(), (Object[]) arguments(messageResolver));
   }

   private String linkFormat() {
      return this.cssClass == null? LINK_FORMAT_WITHOUT_CSS_CLASS : LINK_FORMAT;
   }

   private Serializable[] arguments(MessageResolver messageResolver) {
      final List<Serializable> arguments= new ArrayList<Serializable>();

      final String title= this.text == null? null : ((Resolvable) this.text).resolve(messageResolver);

      arguments.add(this.url);
      arguments.add(StringUtils.defaultString(title, this.url));
      if (StringUtils.isNotBlank(this.cssClass)) {
         arguments.add(StringUtils.defaultString(this.cssClass));
      }
      arguments.add(StringUtils.defaultString(title, this.url));

      return arguments.toArray(new Serializable[0]);
   }

   @Override
   public String toString() {
      return String.format("link{%s, %s, %s}", this.url, this.text, this.cssClass);
   }
}
