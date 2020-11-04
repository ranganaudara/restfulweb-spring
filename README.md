# Spring Boot Learning Repository

This repo is a simple restful web application created to learn and practice spring boot.

### Used Dependancies
- Spring Web --> Restful with MVC, Apache tomcat as embedded container
- Spring data JPA --> For using spring data and hibernate
- Spring boot dev tools --> For fast startup, live reload, initial configurations
- H2 Database --> In-memory database
- Spring HATEOAS --> Contain links in the response
- Spring Validation --> To validate requests
- springfox-swagger-ui 
- springfox-swagger2 --> For API documentation
- spring-boot-starter-actuator --> get data about the server
- spring-data-rest-hal-explorer --> JSON Hypertext Application Language, or HAL, is a simple format that gives a consistent and easy way to hyperlink between resources in our API. HAL browser provides an in-browser GUI to traverse your REST API.

### Filtering
#### Static Filtering
 1. Create FilteringController class
```java
    @RestController  
    public class FilteringController {  
        @GetMapping(path = "/filtering")  
        public SomeBean retrieveSomeBean() {  //this bean should return filtered values
            return new SomeBean("value1", "value2", "value3");  
      }  
    } 
```

 3. Create SomeBean Class
 ```java
public class SomeBean {  
	private String field1;  
	private String field2;
	@JsonIgnore  // This annotation will filter out 'flied3' from response
	private String field3;  
  
 public SomeBean(String field1, String field2, String field3) {  
	 this.field1 = field1;  
	 this.field2 = field2;  
	 this.field3 = field3;  
  }
  //getters and setters...
 }
 ```
 You can use `@JsonIgnoreProperties(value={"field1","field2"})` for the class to ignore multiple fields. Make sure to import these from `com.fasterxml.jackson.annotation.JsonIgnore`.

#### Dynamic Filtering

 Create ``FilteringControl`` class
```java
@RestController  
public class FilteringController {  
  
  //send field1, field2  
  @GetMapping(path = "/filtering")  
  public MappingJacksonValue retrieveSomeBean() {  
	  SomeBean someBean =  new SomeBean("value1", "value2", "value3");  
	  MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);  
	  SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");  
	  FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);  
	  mappingJacksonValue.setFilters(filters);
	  return mappingJacksonValue;  
  }  
  
  //send field2, field3  
  @GetMapping(path = "/filtering-list")  
  public MappingJacksonValue retrieveListOfSomeBean() {  
        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value12", "value22", "value32"));  
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);  
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");  
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;  
  }  
}
```
``SomeBean`` class should be annotated with filter id: ``@JasonFilter("SomeBeamFilter")``

