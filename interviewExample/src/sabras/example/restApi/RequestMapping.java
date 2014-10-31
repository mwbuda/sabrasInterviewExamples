package sabras.example.restApi;

public @interface RequestMapping {

	public String value() default "" ;
	public String method() default "GET" ;
	
}
