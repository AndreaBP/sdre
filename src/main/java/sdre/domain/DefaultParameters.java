package sdre.domain;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public @NoArgsConstructor @Getter @Setter class DefaultParameters {

	private Map <String,Object> arguments;
	
	private String message;
	
}
