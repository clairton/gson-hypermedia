# vraptor-hypermedia
Possibilidade de usar Hypermedia em uma aplicação REST construida com o VRaptor.

O será necessário implementar um serviço reponsável por definir a navegação, por exemplo:
```java
@ApplicationScoped
public class HypermediableRoleStub implements HypermediableRole {

	@Override
	public Set<Link> from(String resource, String operation) {
		final Set<Link> links = new HashSet<>();
		//alguma logica para retornar os links
		return links;
	}

}
```
Para poder serializar uma Entidade com hypermedia, a mesma precisará implementar Hypermediable,
como no exemplo:
Obs: Precisa ser pensando em um forma melhor, pois desta, o model conhece o controller!
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
E na hora de serializar no result use o tipo Results.json(), passando o método atual do controller e a sua instância:

```java
private @Inject Result result;
....
result
	.use(Resuls.json())
	.from(new Pessoa())
	.serialize();
```
O exemplo acima irá retornar algo parecido com:
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

Para usar com o maven, adicione os repositórios:
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
