package validators;

import java.util.HashMap;
import java.util.Map;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import models.User;

import play.Logger;

@SuppressWarnings("serial")
public class UniqueFieldCheck extends AbstractAnnotationCheck<UniqueField> {

    String fieldName;

    @Override
    public void configure(UniqueField uniqueField) {
        this.fieldName = uniqueField.value();
        setMessage(uniqueField.message());
    }
    
    public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
        requireMessageVariablesRecreation();

        if (value == null)
            return false;

        if (value instanceof String) {
            if ("name".equals(fieldName))
              return User.getByName(value.toString()) == null;
        }

        return false;
    }

    @Override
    public Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = new HashMap<String, String>();
        messageVariables.put("field", fieldName);
        return messageVariables;
    }

}
