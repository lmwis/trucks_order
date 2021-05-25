package com.example.trucks_order.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lmwis on 2019-02-22 17:29
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * Spring在初始化所有bean之后会回调这个方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //将validator实例化为hibernate的validator
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();

        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size()>0){
            result.setHasErrors(true);
            Iterator<ConstraintViolation<Object>> it = constraintViolationSet.iterator();
            while(it.hasNext()){
                ConstraintViolation<Object> constraintViolation = it.next();
                String errMsg = constraintViolation.getMessage();
                String propertiesName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertiesName,errMsg);
            }

        }
        return result;
    }
}
