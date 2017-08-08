package sdre.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.validation.Constraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sdre.domain.DefaultParameters;

@Component
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private EntityManager em;

	@SuppressWarnings("rawtypes")
	public Map<String, Map<String, Map<String, DefaultParameters>>> entity(Object ent) {
		
		Map<String, Map<String, Map<String, DefaultParameters>>> entityMap = new HashMap<>();

		String entityName = ((EntityType) ent).getName();
		entityMap.put(entityName, fields((EntityType) ent));

		return entityMap;

	}

	public Map<String, Map<String, Map<String, DefaultParameters>>> entities() {
		
		Map<String, Map<String, Map<String, DefaultParameters>>> entitiesMap = new HashMap<>();
		Set<EntityType<?>> entities = em.getMetamodel().getEntities();

		for (EntityType<?> entity : entities) {
			
			entitiesMap.put(entity.getName(), fields(entity));
			
		}
		return entitiesMap;
	}

	private Map<String, Map<String, DefaultParameters>> fields(EntityType<?> entity) {

		Map<String, Map<String, DefaultParameters>> fieldsMap = new HashMap<>();
		Field[] fields = entity.getJavaType().getDeclaredFields();

		for (Field field : fields) {
			fieldsMap.put(field.getName(), annotations(field));
		}

		return fieldsMap;
	}

	private Map<String, DefaultParameters> annotations(Field field) {

		Map<String, DefaultParameters> annotationsMap = new HashMap<>();

		for (Annotation annotation : field.getAnnotations()) {
			
			annotationsMap.putAll(annotation.annotationType().isAssignableFrom(Column.class) ? columns(field.getAnnotation(Column.class)) : validations(annotation));
		}

		return annotationsMap;
	}

	private Map<String, DefaultParameters> validations(Annotation annotation) {
		Map<String, DefaultParameters> validationsMap = new HashMap<>();

		for (Annotation ann : annotation.annotationType().getAnnotations()) {
			
			if (ann.annotationType().isAssignableFrom(Constraint.class)) {

				validationsMap.putAll(constraintAnnotation(annotation));
			}
		}
		return validationsMap;
	}

	private Map<String, DefaultParameters> constraintAnnotation(Annotation annotation) {
		// TODO: habría que ver alguna forma de que no esté tan hardcodeado (?)
		Map<String, DefaultParameters> constraintMap = new HashMap<>(); 

		DefaultParameters defParams = new DefaultParameters();

		Map<String, Object> defParamsArgs = new HashMap<String, Object>();

		switch (annotation.annotationType().getSimpleName()) {
		case "AssertFalse":
			defParamsArgs.put("assert", false);

			defParams.setMessage("El valor debe ser falso");
			defParams.setArguments(defParamsArgs);
			break;
		case "AssertTrue":
			defParamsArgs.put("assert", true);

			defParams.setMessage("El valor debe ser verdadero");
			defParams.setArguments(defParamsArgs);
			break;
		case "DecimalMax":
			defParamsArgs.put("value", ((DecimalMax) annotation).value());
			defParamsArgs.put("inclusive", ((DecimalMax) annotation).inclusive());

			if (((DecimalMax) annotation).inclusive()) {
				defParams.setMessage("El valor debe ser menor o igual que {value}");
			} else {
				defParams.setMessage("El valor debe ser menor que {value}");
			}

			defParams.setArguments(defParamsArgs);

			break;
		case "DecimalMin":
			defParamsArgs.put("value", ((DecimalMin) annotation).value());
			defParamsArgs.put("inclusive", ((DecimalMin) annotation).inclusive());

			if (((DecimalMin) annotation).inclusive()) {
				defParams.setMessage("El valor debe ser mayor o igual que {value}");
			} else {
				defParams.setMessage("El valor debe ser m que {value}");
			}

			defParams.setArguments(defParamsArgs);
			break;
		case "Digit":
			defParamsArgs.put("integer", ((Digits) annotation).integer());
			defParamsArgs.put("fraction", ((Digits) annotation).fraction());

			defParams.setMessage("El el valor entero más alto aceptado {integer} y fraccional {fraction}");
			defParams.setArguments(defParamsArgs);
			break;
		case "Future":
			defParamsArgs.put("future", true);

			defParams.setMessage("Future");
			defParams.setArguments(defParamsArgs);
			break;
		case "Max":
			defParamsArgs.put("Max", ((Max) annotation).value());

			defParams.setMessage("El valor debe ser meno o igual que {max}");
			defParams.setArguments(defParamsArgs);
			break;
		case "Min":
			defParamsArgs.put("Min", ((Min) annotation).value());

			defParams.setMessage("El valor debería ser meno o igual que {min}");
			defParams.setArguments(defParamsArgs);
			break;
		case "NotNull":
			defParamsArgs.put("NotNull", true);

			defParams.setMessage("El valor no debe ser nulo");
			defParams.setArguments(defParamsArgs);
			break;
		case "Null":
			defParamsArgs.put("Null", true);

			defParams.setMessage("Elv valor debe ser nulo");
			defParams.setArguments(defParamsArgs);
			break;
		case "Past":
			defParamsArgs.put("Past", true);

			defParams.setMessage("Past");
			defParams.setArguments(defParamsArgs);
			break;
		case "Pattern":
			defParamsArgs.put("regexp", ((Pattern) annotation).regexp());
			defParamsArgs.put("flags", ((Pattern) annotation).flags());

			defParams.setMessage("Debe seguir el patrón {regexp}");
			defParams.setArguments(defParamsArgs);
			break;
		case "Size":
			defParamsArgs.put("max", ((Size) annotation).max());
			defParamsArgs.put("min", ((Size) annotation).min());

			defParams.setMessage("El tamanyo tiene que estar entre {min} y {max}");
			defParams.setArguments(defParamsArgs);
			break;

		}
		constraintMap.put(annotation.annotationType().getSimpleName().toLowerCase(), defParams);

		return constraintMap;
	}

	private Map<String, DefaultParameters> columns(Column column) {
		Map<String, DefaultParameters> columnMap = new HashMap<String, DefaultParameters>();
		DefaultParameters defParams = new DefaultParameters();

		// Nullable
		if (!column.nullable()) {
			defParams = getColumnDefaultParameters(column, "required");
		}
		columnMap.put("required", defParams);

		// Length
		defParams = getColumnDefaultParameters(column, "length");

		columnMap.put("length", defParams);

		// Precision
		defParams = getColumnDefaultParameters(column, "precision");

		columnMap.put("precision", defParams);

		// Scale
		defParams = getColumnDefaultParameters(column, "scale");

		columnMap.put("scale", defParams);

		return columnMap;
	}

	private DefaultParameters getColumnDefaultParameters(Column column, String param) {
		Map<String, Object> defParamsArgs = new HashMap<String, Object>();
		DefaultParameters defParams = new DefaultParameters();
		switch (param) {
		case "required":
			defParamsArgs.put("required", true);

			defParams.setMessage("Requerido");
			defParams.setArguments(defParamsArgs);
			break;
		case "length":
			defParamsArgs.put("max", column.length());

			defParams.setMessage("Tamaño maximo: {max}");
			defParams.setArguments(defParamsArgs);
			break;
		case "precision":
			defParamsArgs.put("precision", column.precision());

			defParams.setMessage(param);
			defParams.setArguments(defParamsArgs);
			break;
		case "scale":
			defParamsArgs.put("scale", column.scale());

			defParams.setMessage(param);
			defParams.setArguments(defParamsArgs);
			break;
		}
		return defParams;
	}

}
