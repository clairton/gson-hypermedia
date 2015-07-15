# gson-hypermedia[![Build Status](https://drone.io/github.com/clairton/gson-hypermedia/status.png)](https://drone.io/github.com/clairton/gson-hypermedia/latest)
Possibilidade de usar Hypermedia com o GSON.

O será necessário implementar um serviço reponsável por definir a navegação, por exemplo:
```java
@ApplicationScoped
public class HypermediableRuleStub implements HypermediableRule {

	@Override
	public Set<Link> from(final Collection<Hypermediable> model,
			final String resource, final String operation) {
		final Set<Link> links = new HashSet<>();
		//alguma logica para retornar os links
		return links;
	}
	
	@Override
	public Set<Link> from(final Hypermediable model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		//alguma logica para retornar os links
		return links;
	}

}
```
Para poder serializar uma Entidade com hypermedia, será necessário criar algumas classes.
No caso, temos uma entidade chamada Model, e um serializer para coleção, outro para uma instancia unica.
```java
class ModelSerializer extends
		HypermediableSerializer<Model> implements
		JsonSerializer<Model> {

	public ModelSerializer(
			HypermediableRule<Model> navigator, String operation,
			String resource) {
		super(navigator, operation, resource);
	}

}


class ModelCollectionSerializer extends
		HypermediableCollectionSerializer<Model> implements
		JsonSerializer<Collection<Model>> {

	public ModelCollectionSerializer(
			HypermediableRule<Model> navigator, String operation,
			String resource, Inflector inflector) {
		super(navigator, operation, resource, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}
}
```
Use o tipo Results.json() na hora de serializer:

```java
new Pessoa())
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

Para coleções:

```java
Arrays.asList(new Pessoa()), "pessoas")
```
O exemplo acima irá retornar algo parecido com:
```javascript
{  
   "pessoas":[{  
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
   }],
  "links":[  
     {  
        "href":"/pessoas/new",
        "rel":"new",
        "title":"Criar",
        "method":"GET",
        "type":"application/json"
     }
  ]
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
    <artifactId>gson-hypermedia</artifactId>
    <version>0.1.0-SNAPSHOT</version><!--Ou versão mais recente-->
</dependency>
```
