package sdre.event;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.metamodel.EntityType;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import sdre.controller.EntityManagerController;
import sdre.domain.DefaultParameters;

public class BeforeSaveEventListener extends AbstractRepositoryEventListener{
	
	private EntityManagerController emc = new EntityManagerController();

	@Override
	public void onBeforeSave(Object entity) {
		
	}
}
