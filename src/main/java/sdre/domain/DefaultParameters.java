package sdre.domain;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @NoArgsConstructor @Getter @Setter class DefaultParameters implements Serializable{

	private Map <String,Object> arguments;
	
	private String message;
	
}
