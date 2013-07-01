package validators;

import java.util.Collection;
import java.util.List;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.MethodParameterContext;
import net.sf.oval.context.OValContext;
import play.exceptions.UnexpectedException;
import play.utils.Java;
import play.data.validation.ValidationPlugin;
import play.data.validation.Required;
import play.data.validation.Validation;

import play.Logger;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import annotations.PoliticianOnly;

@SuppressWarnings("serial")
public class ValidCitizenCheck extends AbstractAnnotationCheck<Required> {

    final static String message = "validation.object";
    String key;

    public boolean isSatisfied(Object validatedObject, Object value, OValContext context, Validator validator) {
        String superKey = ValidationPlugin.keys.get().get(validatedObject);
        if (value == null) {
            return true;
        }
        try {
            if (context != null) {
                if (context instanceof MethodParameterContext) {
                    MethodParameterContext ctx = (MethodParameterContext) context;
                    String[] paramNames = Java.parameterNames(ctx.getMethod());
                    key = paramNames[ctx.getParameterIndex()];
                }
                if (context instanceof FieldContext) {
                    FieldContext ctx = (FieldContext) context;
                    key = ctx.getField().getName();
                }
            }
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
        if (superKey != null) {
            key = superKey + "." + key;
        }

        if(value instanceof Collection) {
            Collection valueCollection = (Collection)value;
            boolean everythingIsValid = true;
            int index = 0;
            for(Object item : valueCollection) {
                if(!validateObject(key + "[" + (index) + "]", item)) {
                    Validation.current().addError(key + "[" + (index) + "]", message, new String[0]);
                    everythingIsValid = false;
                }
                index++;
            }
            if(!everythingIsValid) {
                return false;
            } else {
                return true;
            }
        } else {
            return validateObject(key, value);
        }
    }

    boolean validateObject(String key, Object value) {
        ValidationPlugin.keys.get().put(value, key);
        List<ConstraintViolation> violations = new Validator().validate(value);
        //
        if (violations.isEmpty()) {
            return true;
        } else {
            for (ConstraintViolation violation : violations) {
                if (violation.getContext() instanceof FieldContext) {
                    final FieldContext ctx = (FieldContext) violation.getContext();
                    final String fkey = (key == null ? "" : key + ".") + ctx.getField().getName();

                    /* If this field has got PoliticianOnly annotation, then we discard its validation error */
                    Field f = (Field)ctx.getField();
                    if (!f.isAnnotationPresent(annotations.PoliticianOnly.class)) {
                      Logger.info("Añado error a %s", fkey);
                      Validation.current().addError(fkey, violation.getMessage(), violation.getMessageVariables() == null ? new String[0] : violation.getMessageVariables().values().toArray(new String[0]));
                    }
                }
            }

            /* Only return object validation failed when there are any non discarded validation errors */
            if (Validation.current().errors().isEmpty())
              return true;
            else
              return false;
        }
    }
    
}
