# vraptor-hypermedia
Possibilidade de usar Hypermedia em uma aplicação REST construido com vraptor

O controller que irá fornercer o recurso precisa implementar HypermediableController
por exemplo:
```java
@Controller
public class PessoaController implements HypermediableController {
    ...
	@Override
	public Set<Link> links(final String operation) {
		final Set<Link> links = new HashSet<>();
		if ("show".equals(operation)) {
			links.add(new Link("/pessoas/1", "update", "Salvar", "PUT", "application/json"));
			//alguma outra lógica obuscura para recuperar os links que o usuário tem acesso
		}else{
			return links;
		}
	}
}
```
Para poder serilizar a Entidade precisará implementar Hypermediable, como no exemplo:
```java
public class Pessoa implements Hypermediable {
	private Integer id;
	private final String nome;
	private Set<Link> links = new HashSet<>();

	...

	@Override
	public Set<Link> getlinks() {
		return links;
	}
}
```
E na hora de serializar no result use o tipo jsonHypermedia, passando o método atual do controller e a sua instância:

```java
import static br.eti.clairton.vraptor.hypermedia.HypermediaJsonSerialization.jsonHypermedia;
...
private @Inject Result result;
....
result
	.use(jsonHypermedia())//serializer customizado
	.self("show")//seta o método atual
	.links(controller)//a instancia do controller que irá devolver a navegação
	.from(new Pessoa())
	.recursive()//de forma recursiva para seriliazer o set chamado links
	.serialize();
```
O exemplo acima irá retornar:
```javascript
{  
   "pessoa":{  
      "id":1,
      "nome":"Maria",
      "links":[  
         {  
            "href":"/pessoas/1",
            "rel":"update",
            "title":"Salvar",
            "method":"PUT",
            "type":"application/json"
         }
      ]
   }
}
```


```xml
<repository>
	<id>mvn-repo-releases</id>
	<url>https://raw.github.com/clairton/mvn-repo/releases</url>
</repository>
<repository>
	<id>mvn-repo-snapshot</id>
	<url>https://raw.github.com/clairton/mvn-repo/snapshots</url>
</repository>
```
 Também adicionar as depêndencias:
```xml
<dependency>
    <groupId>br.eti.clairton</groupId>
    <artifactId>vraptor-hypermedia</artifactId>
    <version>0.1.0-SNAPSHOT</version><!--Ou versão mais recente-->
</dependency>
```
